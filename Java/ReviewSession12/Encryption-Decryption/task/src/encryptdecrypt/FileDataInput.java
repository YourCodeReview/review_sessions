package encryptdecrypt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDataInput implements DataIn{
    @Override
    public String getData(String file, String data) {
        String fileData = "";
        try{
            fileData = new String(Files.readAllBytes(Paths.get(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: file not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: IO Exception");
        }

        return fileData;
    }
}
