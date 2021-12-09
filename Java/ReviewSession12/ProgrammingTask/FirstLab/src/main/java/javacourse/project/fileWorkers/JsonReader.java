package javacourse.project.fileWorkers;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import javacourse.project.data.Person;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class JsonReader {
    private static final Logger log = Logger.getLogger(JsonReader.class);
    private static LinkedHashMap<Long, Person> linkedHashMap;

    public  LinkedHashMap<Long,Person> read(String adres, LinkedHashMap<Long,Person> personMap) {
        linkedHashMap = personMap;
        File file = new File(adres);
        try (FileReader fileReader = new FileReader(adres)) {
            deserializeFromJSON(fileReader);
            System.out.println("Коллекция успешно загружена. Элементов в коллекции " + linkedHashMap.size());
        } catch (FileNotFoundException e) {
            log.error(e);
            if (!(file.exists())) {
                System.out.println("Данного файла не существует");
            }
            if (!(file.canRead())) {
                System.out.println("У вас нету прав для чтения файла");
            }
            if (file.isDirectory()) {
                System.out.println("Вы передали адрес директории, а нужно было адрес файла");
            }
            /*
            TODO как вообще здесь лучше обработать ошибки? если я хочу дать полную информацию о причине. Поэтому проверяю на разные случаи*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkedHashMap;
    }

    private  void deserializeFromJSON(FileReader fileReader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).setPrettyPrinting().create();
            LinkedHashMap<Long, Person> hashMap = gson.fromJson(bufferedReader, new TypeToken<LinkedHashMap<Long, Person>>() {
            }.getType());
            addToDatabase(hashMap);
        } catch (JsonSyntaxException | IllegalStateException e ) {
            log.error(e );
            System.out.println("Не получилось десериализовать json файл. Данные не корректны");
        }finally
         {
            fileReader.close();
        }
    }
    private  void addToDatabase(LinkedHashMap<Long, Person> personLinkedHashMap) {
        if (!(personLinkedHashMap==null)){
            linkedHashMap.putAll(personLinkedHashMap);
        }
    }
}
