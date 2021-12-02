package encryptdecrypt;

import java.util.stream.Collectors;

public class ShiftDecode implements CipherAlgorithm{
    @Override
    public String executeCipher(String data, int key) {
        data = data.chars()
                .map(a -> {
                    if(a >= FIRST_LETTER_OF_ALPHABET && a <= LAST_LETTER_OF_ALPHABET) {
                        return a - key < FIRST_LETTER_OF_ALPHABET ? LAST_LETTER_OF_ALPHABET + (a - key - FIRST_LETTER_OF_ALPHABET) + 1 : a - key;
                    } else if(a >= FIRST_CAPS_LETTER_OF_ALPHABET && a <= LAST_CAPS_LETTER_OF_ALPHABET) {
                        return a - key < FIRST_CAPS_LETTER_OF_ALPHABET ? LAST_CAPS_LETTER_OF_ALPHABET + (a - key - FIRST_CAPS_LETTER_OF_ALPHABET) + 1 : a - key;
                    } else {
                        return a;
                    }
                })
                .mapToObj(a -> (char)a)
                .map(String::valueOf)
                .collect(Collectors.joining());
        return data;
    }
}
