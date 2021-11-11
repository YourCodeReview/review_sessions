package com.simbirsoft.testwork.model;

import com.simbirsoft.testwork.helper.SplitHelper;
import com.simbirsoft.testwork.storage.MapStorage;
import com.simbirsoft.testwork.util.JsoupParser;

import java.util.List;

public class Program {
    private String url;

    public Program(String url) {
        this.url = url;
    }

    public void startProgram() {
        JsoupParser webSite = new JsoupParser(url);
        webSite.readDocument();
        List<String> wordsInText = SplitHelper.splitAndSortText(webSite.readText());
        MapStorage storage = new MapStorage();
        storage.save(wordsInText, url);
        storage.printAllWords();
    }
}
