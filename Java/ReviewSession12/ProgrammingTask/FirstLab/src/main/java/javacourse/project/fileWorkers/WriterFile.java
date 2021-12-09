package javacourse.project.fileWorkers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import javacourse.project.data.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

//TODO нормально обработать ошибки, если файла не существует, или нет прав для записи
public class WriterFile {
    private static final Logger log = Logger.getLogger(WriterFile.class);
    private String adress;
    private LinkedHashMap<Long, Person> linkedHashMap;
//TODO разобраться когда где и как обрабатывать валидация данных файла

    public WriterFile(String adress) {
        this.adress = adress;
    }

    public void write(LinkedHashMap<Long, Person> linkedHashMap) {
        this.linkedHashMap = linkedHashMap;
        File file = new File(adress);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            serializeToJSON(bufferedWriter);
        } catch (IOException e) {
            log.error(e);
            if (!(file.canWrite())) {
                System.out.println("У вас нету прав для запись в файл");
            }else{
            if (file.isDirectory()) {
                System.out.println("Вы передали адрес директории, а нужно было адрес файла");
            }}
        }

    }
    private void serializeToJSON(BufferedWriter writer) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).setPrettyPrinting().serializeNulls().create();
        writer.write(gson.toJson(linkedHashMap));
        writer.close();
    }
}



   /*     Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }).create();*/
/*        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
                  gson.toJson(linkedHashMap, writer);
        writer.close();*/
// }).create();