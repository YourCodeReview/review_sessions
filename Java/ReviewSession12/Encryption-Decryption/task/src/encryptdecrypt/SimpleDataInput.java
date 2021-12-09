package encryptdecrypt;

public class SimpleDataInput implements DataIn{

    @Override
    public String getData(String file, String data) {
        return data;
    }
}
