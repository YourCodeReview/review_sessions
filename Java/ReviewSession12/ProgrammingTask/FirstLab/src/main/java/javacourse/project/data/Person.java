package javacourse.project.data;

import lombok.Data;
import javacourse.project.collection.PersonStorage;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class Person implements  Comparable<Person>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long height; //Поле может быть null, Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday; //Поле может быть null
    private String passportID; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Location location; //Поле не может быть null
    public Person(long id, String name){
        this.id = id;
        this.name=name;
    }

    public Person(String name, Coordinates coordinates, Long height, LocalDateTime birthday, String passportID, Color hairColor, Location location) {
        this.id = PersonStorage.generateID();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = Date.from(Instant.now());
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (!name.equals(person.name)) return false;
        if (!coordinates.equals(person.coordinates)) return false;
        if (creationDate != null ? !creationDate.equals(person.creationDate) : person.creationDate != null)
            return false;
        if (height != null ? !height.equals(person.height) : person.height != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;
        if (passportID != null ? !passportID.equals(person.passportID) : person.passportID != null) return false;
        if (hairColor != person.hairColor) return false;
        return location.equals(person.location);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + coordinates.hashCode();
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (passportID != null ? passportID.hashCode() : 0);
        result = 31 * result + hairColor.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Person{\n " +
                "id=" + id +
                ",\n name='" + name + '\'' +
                ",\n coordinates=" + coordinates +
                ",\n creationDate=" + creationDate +
                ",\n height=" + height +
                ",\n birthday=" + birthday +
                ",\n passportID='" + passportID + '\'' +
                ",\n hairColor=" + hairColor +
                ",\n location=" + location +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.birthday.compareTo(o.birthday);
    }
}
