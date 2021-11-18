package com.simbirsoft.testwork.storage;

import com.simbirsoft.testwork.config.SqlConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage implements Storage {

    private final Map<String, Integer> storage = new HashMap<>();

    public void save(List<String> listWords, String url) {
        for (String word : listWords) {
            Integer oldCount = storage.get(word);
            if (oldCount == null) {
                oldCount = 0;
            }
            storage.put(word, oldCount + 1);
        }
        saveInDataBase(storage, url);
    }

    public void clear() {
        storage.clear();
    }

    public Map<String, Integer> getStorage() {
        return storage;
    }

    public void printAllWords() {
        for (Map.Entry<String, Integer> item : storage.entrySet()) {
            System.out.printf("%s : %s\n", item.getKey(), item.getValue());
        }
    }

    public int size() {
        return storage.size();
    }

    private void saveInDataBase(Map<String, Integer> storage, String url) {
        DataBase db = new DataBase(SqlConfig.getProperties(SqlConfig.DB_URL),
                SqlConfig.getProperties(SqlConfig.DB_USER),
                SqlConfig.getProperties(SqlConfig.DB_PASSWORD));
        db.createTable(url);
        db.doSave(storage, url);
    }

}

