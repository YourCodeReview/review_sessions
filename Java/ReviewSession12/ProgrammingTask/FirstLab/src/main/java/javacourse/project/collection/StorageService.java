package javacourse.project.collection;


import javacourse.project.data.Location;
import javacourse.project.data.Person;
import javacourse.project.fileWorkers.WriterFile;

import java.time.LocalDateTime;

public interface StorageService {
     void show();
     void info();
     void insertKey(long key, Person person);
     void updateID(long key,Person person);
     void removeKey(long key);
     void clear();
     void save(WriterFile writerFile);
     void exit();
     void removeGreater(Person person);
     void removeGreaterKey (long key);


     void removeAnyByBirthday(LocalDateTime date);

     long countLessThanLocation (Location location);
     void printAscending();
     boolean checkKey(long key);
     boolean checkId(long id);
     Person getPersonById(long id);
     void updateSetId();
}
