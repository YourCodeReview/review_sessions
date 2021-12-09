package javacourse.project.collection;


import javacourse.project.data.Person;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonStorage implements Storage<Long, Person> {
    private LinkedHashMap<Long, Person> personMap = new LinkedHashMap<>();
    private final Date creationalDate;
    public static HashSet<Long> IDset = new HashSet<>();
    private static long maxID;
    public PersonStorage() {
        this.creationalDate = new Date();
    }

    @Override
    //TODO доработать уникальность id
    public void add(long key, Person person) {
        personMap.put(key, person);
    }

    @Override
    public void remove(long key) {
        personMap.remove(key);
    }

    @Override
    public void remove(long key, Person value) {
        personMap.remove(key,value);
    }

    @Override
    public Person getByKey(long key) {
        return personMap.get(key);
    }

    @Override
    public int size() {
        return personMap.size();
    }

    @Override
    public void clear() {
        personMap.clear();
    }

    @Override
    public void replace(long key, Person person) {
        personMap.replace(key, person);
    }

    @Override
    public Date getInitializationTime() {
        return creationalDate;
    }

    @Override
    public Class<?> getCollectionClass() {
        return personMap.getClass();
    }

    @Override
    public LinkedHashMap<Long, Person> getCollection() {
        return personMap;
    }

    @Override
    public Person getValueById(long id) {
        return personMap.values().stream().filter(x->x.getId()==id).findFirst().orElse(null);
    }
    @Override
    public long getKeyByValue(Person person){
        for (Map.Entry<Long, Person> pair: personMap.entrySet()){
            if (person.equals(pair.getValue())) return pair.getKey();
        }
        return -1;
    }
    public static long generateID(){
        while (IDset.contains(maxID)){
            maxID++;

        }
        return maxID;
    }
}
