package encryptdecrypt;

import java.util.stream.Collectors;

public class UnicodeEncode implements CipherAlgorithm{
    @Override
    public String executeCipher(String data, int key) {
        data = data.chars()
                .map(a -> a + key)
                .mapToObj(a -> (char)a)
                .map(String::valueOf)
                .collect(Collectors.joining());
        return data;
    }
}
