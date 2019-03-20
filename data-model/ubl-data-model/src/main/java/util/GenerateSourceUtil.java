package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateSourceUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final private String partyTypeRegex = "@ManyToOne.targetEntity = PartyType.class,.*public PartyType";
    final private String cascadeRegex = "@Cascade.*DELETE_ORPHAN.*@OneToMany.";
    final private String regex_transientListsWithOrphanRemovals = "@Cascade.*\\s*.*DELETE_ORPHAN\\s*.*\\s+@Transient\\s*.*List.*";
    final private String regex_transientListDefs = "protected transient List.*";
    final private String regex_builtInListWithOrm = "target.*\\s+.*\\s+.*\\s+.*\\s+.*";

    public static void main(String [] args){
        GenerateSourceUtil generateSourceUtil = new GenerateSourceUtil();
        generateSourceUtil.postProcessORMAnnotations(args[0]);
    }

    public void postProcessORMAnnotations(String path){
        logger.debug("Started to change party cascade types");
        File directory = new File(path);
        searchDirectory(directory);
        logger.debug("Changed party cascade types successfully");
    }

    public void searchDirectory(File directory){
        File[] filesAndDirs = directory.listFiles();
        for(File file : filesAndDirs){
            if(file.isFile()){
                String fileContent = getFileContent(file);
                FileUpdate fileUpdate = new FileUpdate();
                fileUpdate.setContent(fileContent);
                searchRegex(fileUpdate);
                removeOrphanRemovalFromTransientLists(fileUpdate);
                addOrphanRemovalsToTransientLists(fileUpdate);

                if(file.getName().contentEquals("TextType.java")) {
                    updateTextTypeValueField(fileUpdate);
                }

                updateFile(file, fileUpdate);
            }
            else {
                searchDirectory(file);
            }
        }
    }

    public void searchRegex(FileUpdate fileUpdate){
        try {
            String fileText = fileUpdate.getContent();
            Pattern p = Pattern.compile(partyTypeRegex,Pattern.DOTALL);
            Pattern p2 = Pattern.compile(cascadeRegex,Pattern.DOTALL);
            Matcher m = p.matcher(fileText);
            Matcher m2 = p2.matcher(fileText);

            boolean firstFound = m.find();
            boolean secondFound = m2.find();
            // now try to find at least one match
            if (firstFound){
                String group = m.group();
                // first replace the annotations with full package names
                String newGroup = group.replace("javax.persistence.CascadeType.ALL","javax.persistence.CascadeType.PERSIST,javax.persistence.CascadeType.MERGE,javax.persistence.CascadeType.REFRESH");
                // in case the annotations do not have full package names, the line below has effect
                newGroup = newGroup.replace("CascadeType.ALL","javax.persistence.CascadeType.PERSIST,javax.persistence.CascadeType.MERGE,javax.persistence.CascadeType.REFRESH");
                fileText = fileText.replace(group,newGroup);
            }
            if(secondFound){
                fileText = fileText.replaceAll("@Cascade.+\\s+org.hibernate.annotations.CascadeType.DELETE_ORPHAN\\s+.+\\s+@OneToMany.","@OneToMany(orphanRemoval = true,");
                fileText = fileText.replaceAll("@Cascade.+\\s+org.hibernate.annotations.CascadeType.DELETE_ORPHAN\\s+.+\\s+@OneToOne.","@OneToOne(orphanRemoval = true,");
            }

            if(firstFound || secondFound){
                fileUpdate.setFileUpdated(true);
                fileUpdate.setContent(fileText);
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to change cascade type of parties",e);
        }
    }

    private void addOrphanRemovalsToTransientLists(FileUpdate fileUpdate) {
        String fileContent = fileUpdate.getContent();
        // name of transient fields
        List<String> toBeAnnotatedMethods = new ArrayList<>();
        Pattern p = Pattern.compile(regex_transientListDefs);
        Matcher m = p.matcher(fileContent);

        if(m.find()) {
            m.reset();
            while(m.find()) {
                String matchingPart = m.group();
                String fieldName = matchingPart.substring(matchingPart.indexOf('>')+2, matchingPart.length()-1);
                String fieldNameToUpdate = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                toBeAnnotatedMethods.add(fieldNameToUpdate);
            }

            for(String methodName : toBeAnnotatedMethods) {
                Pattern p2 = Pattern.compile(regex_builtInListWithOrm + methodName);
                Matcher m2 = p2.matcher(fileContent);
                m2.find();
                String matchingPart = m2.group();
                String replacement = "orphanRemoval = true, " + matchingPart;
                fileContent = fileContent.replaceFirst(regex_builtInListWithOrm + methodName, replacement);
            }

            fileUpdate.setContent(fileContent);
            fileUpdate.setFileUpdated(true);
        }
    }

    private void removeOrphanRemovalFromTransientLists(FileUpdate fileUpdate) {
        String fileContent = fileUpdate.getContent();
        Pattern p = Pattern.compile(regex_transientListsWithOrphanRemovals);
        Matcher m = p.matcher(fileContent);
        if(m.find()) {
            m.reset();
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String matchingPart = m.group();
                String remainder = matchingPart.substring(matchingPart.indexOf("@Transient"));
                m.appendReplacement(sb, remainder);
            }
            fileUpdate.setContent(m.appendTail(sb).toString());
            fileUpdate.setFileUpdated(true);
        }
    }

    private void updateTextTypeValueField(FileUpdate fileUpdate) {
        String fileContent = fileUpdate.getContent();
        fileContent = fileContent.replace("@Column(name = \"VALUE_\", length = 255)", "@Column(name = \"VALUE_\", columnDefinition = \"TEXT\", length = 255)");
        fileUpdate.setContent(fileContent);
        fileUpdate.setFileUpdated(true);
    }

    private void updateFile(File file, FileUpdate fileUpdate) {
        if(!fileUpdate.isFileUpdated()) {
            return;
        }

        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(fileUpdate.getContent());

            BufferedWriter bwr = new BufferedWriter(new FileWriter(file));

            //write contents of StringBuffer to the file
            bwr.write(stringBuffer.toString());

            //flush the stream
            bwr.flush();

            //close the stream
            bwr.close();

        } catch(IOException e) {
            throw new RuntimeException("Failed to update file", e);
        }
    }

    private String getFileContent(File file) {
        FileInputStream fileStream = null;
        BufferedReader br = null;
        InputStreamReader inputStreamReader = null;
        try {
            StringBuffer text = new StringBuffer();
            fileStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileStream);
            br = new BufferedReader(inputStreamReader);
            for (String line; (line = br.readLine()) != null; )
                text.append(line + System.lineSeparator());

            String fileText = text.toString();
            return fileText;

        } catch (IOException e) {
            throw new RuntimeException("Failed to get file content", e);
        } finally {
            if(fileStream != null){
                try {
                    fileStream.close();
                } catch (IOException e) {
                    logger.warn("Failed to close file stream: ",e);
                }
            }
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    logger.warn("Failed to close buffered reader: ",e);
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    logger.warn("Failed to close input stream reader: ",e);
                }
            }
        }
    }

    private static class FileUpdate {
        private boolean fileUpdated = false;
        private String content;

        public boolean isFileUpdated() {
            return fileUpdated;
        }

        public void setFileUpdated(boolean fileUpdated) {
            this.fileUpdated = fileUpdated;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
