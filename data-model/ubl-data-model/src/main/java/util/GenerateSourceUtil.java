package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateSourceUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final private String partyTypeRegex = "@ManyToOne.targetEntity = PartyType.class,.*public PartyType";
    final private String cascadeRegex = "@Cascade.*DELETE_ORPHAN.*@OneToMany.";

    public static void main(String [] args){
        GenerateSourceUtil generateSourceUtil = new GenerateSourceUtil();
        generateSourceUtil.changePartyCascadeTypes(args[0]);
    }

    public void changePartyCascadeTypes(String path){
        logger.debug("Started to change party cascade types");
        File directory = new File(path);
        searchDirectory(directory);
        logger.debug("Changed party cascade types successfully");
    }

    public void searchDirectory(File directory){
        File[] filesAndDirs = directory.listFiles();
        for(File file : filesAndDirs){
            if(file.isFile()){
                searchRegex(file);
            }
            else {
                searchDirectory(file);
            }
        }
    }

    public void searchRegex(File file){
        try {
            StringBuffer text = new StringBuffer();
            FileInputStream fileStream = new FileInputStream( file );
            BufferedReader br = new BufferedReader( new InputStreamReader( fileStream ) );
            for ( String line; (line = br.readLine()) != null; )
                text.append( line + System.lineSeparator() );
            String fileText = text.toString();

            fileStream.close();
            br.close();

            Pattern p = Pattern.compile(partyTypeRegex,Pattern.DOTALL);
            Pattern p2 = Pattern.compile(cascadeRegex,Pattern.DOTALL);
            Matcher m = p.matcher(fileText);
            Matcher m2 = p2.matcher(fileText);

            boolean firstFound = m.find();
            boolean secondFound = m2.find();
            // now try to find at least one match
            if (firstFound){
                String group = m.group();
                String newGroup = group.replace("CascadeType.ALL","CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH");
                fileText = fileText.replace(group,newGroup);
            }
            if(secondFound){
                fileText = fileText.replaceAll("@Cascade.+\\s+org.hibernate.annotations.CascadeType.DELETE_ORPHAN\\s+.+\\s+@OneToMany.","@OneToMany(orphanRemoval = true,");
                fileText = fileText.replaceAll("@Cascade.+\\s+org.hibernate.annotations.CascadeType.DELETE_ORPHAN\\s+.+\\s+@OneToOne.","@OneToOne(orphanRemoval = true,");
            }

            if(firstFound || secondFound){
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(fileText);

                BufferedWriter bwr = new BufferedWriter(new FileWriter(file));

                //write contents of StringBuffer to the file
                bwr.write(stringBuffer.toString());

                //flush the stream
                bwr.flush();

                //close the stream
                bwr.close();
            }

        }
        catch (Exception e){
            logger.error("Failed to change cascade type of parties",e);
        }

    }
}
