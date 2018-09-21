package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateSourceUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final private String partyTypeRegex = "@ManyToOne.targetEntity = PartyType.class,.*public PartyType";

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

            Pattern p = Pattern.compile(partyTypeRegex,Pattern.DOTALL);   // the pattern to search for
            Matcher m = p.matcher(fileText);

            // now try to find at least one match
            if (m.find()){
                StringBuffer stringBuffer = new StringBuffer();

                String group = m.group();
                String newGroup = group.replace("CascadeType.ALL","CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH");
                fileText = fileText.replace(group,newGroup);

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
