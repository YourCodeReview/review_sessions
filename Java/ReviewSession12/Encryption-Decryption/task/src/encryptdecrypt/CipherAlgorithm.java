package encryptdecrypt;

public interface CipherAlgorithm { // interface of cipher execution
    public final static int FIRST_LETTER_OF_ALPHABET = 97;
    public final static int LAST_LETTER_OF_ALPHABET = 122;
    public final static int FIRST_CAPS_LETTER_OF_ALPHABET = 65;
    public final static int LAST_CAPS_LETTER_OF_ALPHABET = 90;
    public String executeCipher(String data, int key);
}
