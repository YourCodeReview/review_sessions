package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileDataOutput implements DataOut{
    @Override
    public void PassData(String file, String data) {
        File out = new File(file);
        try {
            out.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: not possible to create file");
        }

        try(FileWriter w = new FileWriter(out)) {
            w.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: IO Exception");
        }
    }
}
