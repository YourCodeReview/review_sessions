package com.simbirsoft.testwork.storage;

import com.simbirsoft.testwork.exeption.StorageException;
import com.simbirsoft.testwork.helper.SplitHelper;
import com.simbirsoft.testwork.util.JsoupParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapStorageTest {

    public Storage storage = new MapStorage();
    public Map<String, Integer> expectedMap = new HashMap<>();
    String url;
    String expectedLine;
    List<String> wordsInText;


    @BeforeEach
    public void setUp() {
        url = "testedURL";
        expectedLine = "Hello, my, hello. friend";
        wordsInText = SplitHelper.splitAndSortText(expectedLine);
        storage.clear();
        storage.save(wordsInText, url);
    }


    @Test
    void save() {
        expectedMap.put("HELLO", 2);
        expectedMap.put("MY", 1);
        expectedMap.put("FRIEND", 1);
        expectedMap.put("QWERTY", 2);
        storage.clear();
        String words = "Hello, my, hello, friend, qwerty!qwerty";
        storage.save(SplitHelper.splitAndSortText(words), url);
        assertEquals(expectedMap, storage.getStorage());
    }

    @Test
    void size() {
        expectedMap.put("HELLO", 2);
        expectedMap.put("MY", 1);
        expectedMap.put("FRIEND", 1);
        assertEquals(expectedMap.size(), storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void notFoundText() {
        assertThrows(StorageException.class, () -> SplitHelper.splitAndSortText(null), "Not found text in Site");
    }

    @Test
    void incorrectUrl() {
        assertThrows(StorageException.class, () -> new JsoupParser(url), "Incorrect url " + url);
    }


    //Send link on picture
    @Test
    void notFoundTextInSite() {
        assertThrows(StorageException.class, () -> {
            JsoupParser jp = new JsoupParser("https://www.simbirsoft.com/local/templates/.default/img" +
                    "/top_bg/header_images/banner_bg2_1199plus.webp");
            jp.readDocument();
            jp.readText();
        }, "Not found text" + url);

    }
}