package javacourse.project.data;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Coordinates {
    private Long x; //Поле не может быть null
    private Double y; //Значение поля должно быть больше -537

}
