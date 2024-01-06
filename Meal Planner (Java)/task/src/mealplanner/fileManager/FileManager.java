package mealplanner.fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    String filename;


    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void writeFile(String content) {
        File file = new File(filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }
}
