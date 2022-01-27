/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.utils;

import com.ibm.icu.text.Transliterator;

public class TransliteratorUtils {

    private static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    public static String transliterate(String source) {
        return Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(source);
    }
}
