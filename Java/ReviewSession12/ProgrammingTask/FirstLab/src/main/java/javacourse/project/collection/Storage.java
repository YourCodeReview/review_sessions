package javacourse.project.collection;


import javacourse.project.data.Person;

import java.util.Date;
import java.util.LinkedHashMap;

public interface Storage<K, T> {
    void add(long key, T person);

    void remove(long key);

    void remove(long key, Person value);

    Person getByKey(long key);

    int size();

    void clear();

    void replace(long key, Person person);

    Date getInitializationTime();

    Class<?> getCollectionClass();

    LinkedHashMap<K, T> getCollection();

    Person getValueById(long id);

    long getKeyByValue(Person person);
}
