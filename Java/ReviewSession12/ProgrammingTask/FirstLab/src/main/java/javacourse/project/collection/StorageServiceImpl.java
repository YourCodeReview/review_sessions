package javacourse.project.collection;

import javacourse.project.data.Location;
import javacourse.project.data.Person;
import javacourse.project.fileWorkers.WriterFile;

import java.time.LocalDateTime;
import java.util.List;

public class StorageServiceImpl implements StorageService {
    private Storage<Long, Person> personStorage;

    public StorageServiceImpl(Storage<Long, Person> personStorage) {
        this.personStorage = personStorage;
    }

    @Override
    public void show() {
        StringBuilder stringBuilder = new StringBuilder("Количество элементов коллекции: " + personStorage.size()).append("\n");
        personStorage.getCollection().forEach((key, person) -> stringBuilder.append("{ key  = ").append(key).append("\n").append(person.toString()).append("}").append("\n"));
        System.out.println(stringBuilder);
    }


    @Override
    public void info() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Время коллекции:").append(personStorage.getInitializationTime()).append("\n");
        stringBuilder.append("Количество элеменетов в коллекции: ").append(personStorage.size()).append("\n");
        stringBuilder.append("Тип коллекции: ").append(personStorage.getCollectionClass()).append("\n");
        System.out.println(stringBuilder);
    }

    @Override
    public void insertKey(long key, Person person) {
        personStorage.add(key, person);
    }

    @Override
    public void updateID(long id, Person person) {
        Person person1 = personStorage.getValueById(id);
        long key = personStorage.getKeyByValue(person1);
        personStorage.replace(key, person);
    }

    @Override
    public void removeKey(long key) {
        personStorage.remove(key);
    }

    @Override
    public void clear() {
        personStorage.clear();
    }

    @Override
    public void save(WriterFile writerFile) {
        writerFile.write(personStorage.getCollection());
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void removeGreater(Person person) {
        try {
            personStorage.getCollection().entrySet().removeIf(element -> element.getValue().compareTo(person) > 0);
        } catch (NullPointerException e) {
            List<Long> list = personStorage.getCollection().keySet().stream().filter(x -> personStorage.getByKey(x).getBirthday() == null).toList();
            System.out.println("Значения даты рождения некоторых элементов равно null \n " +
                    "Список ключей таких " + list.toString() +
                    "\nПопробуйте задать им другое значение дня рождения с помощью команды update +\n");
        }
    }


    @Override
    public void removeGreaterKey(long key) {
        personStorage.getCollection().entrySet().removeIf(element -> element.getKey() > key);
    }

    @Override
    public void removeAnyByBirthday(LocalDateTime date) {
        personStorage.getCollection().entrySet().removeIf(tempEntry -> tempEntry.getValue().getBirthday().equals(date));
    }

    @Override
    public long countLessThanLocation(Location location) {
        return personStorage.getCollection().values().stream().filter(x -> x.getLocation().compareTo(location) == -1).count();
    }

    @Override
    public void printAscending() {
        try {
            personStorage.getCollection().values().stream().sorted().forEach(System.out::println);
        } catch (NullPointerException e) {
            List<Long> list = personStorage.getCollection().keySet().stream().filter(x -> personStorage.getByKey(x).getBirthday() == null).toList();
            System.out.println("Значения даты рождения некоторых элементов равно null \n " +
                    "Список ключей таких элементов " + list.toString() +
                    "\nПопробуйте задать им другое значение дня рождения с помощью команды update +\n");
        }
    }

    //возвращает true . если не содержит тако ключ
    @Override
    public boolean checkKey(long key) {
        return !personStorage.getCollection().containsKey(key);
    }

    @Override
    public Person getPersonById(long id) {
        return personStorage.getValueById(id);
    }

    @Override
    public boolean checkId(long id) {
        return (personStorage.getCollection().values().stream().anyMatch(x -> x.getId() == id));
    }

    @Override
    public void updateSetId() {
        personStorage.getCollection().values().forEach(x -> PersonStorage.IDset.add(x.getId()));

    }
}