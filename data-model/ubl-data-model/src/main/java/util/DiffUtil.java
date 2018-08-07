package util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

/**
 * Created by suat on 12-Jun-18.
 */
public class DiffUtil {
    public static void main(String[] args) throws IOException {
        LineIterator lineIterator = FileUtils.lineIterator(new File("D:\\srdc\\projects\\NIMBLE\\project_starts\\codes\\common\\diff.txt"));
        while(lineIterator.hasNext()) {
            String line = lineIterator.next();
            if(!line.startsWith("1\t1\t")) {
                System.out.println(line);
            }
        }

        lineIterator = FileUtils.lineIterator(new File("D:\\srdc\\projects\\NIMBLE\\project_starts\\codes\\common\\diff.txt"));

        while(lineIterator.hasNext()) {
            String line = lineIterator.next();
            if(!line.startsWith("1\t1\t")) {
                System.out.print(line.substring(line.lastIndexOf('\t')+1) + " ");
            }
        }
    }
}
