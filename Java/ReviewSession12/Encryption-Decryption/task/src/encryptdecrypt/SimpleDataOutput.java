package encryptdecrypt;

public class SimpleDataOutput implements DataOut{
    @Override
    public void PassData(String file, String data) {
        System.out.println(data);
    }
}
