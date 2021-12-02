package javacourse.project.data;



import java.util.Arrays;

public enum Color {
    GREEN("зеленый"),
    BLACK("черный"),
    YELLOW("желтый"),
    ORANGE("оранжевый"),
    WHITE("белый");
    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public static boolean isPresent(String color){
        return Arrays.stream(Color.values()).anyMatch(element -> element.color.equals(color));
    }
    public static Color getColorByString (String enumType){
        return Arrays.stream(Color.values()).filter(element -> element.color.equals(enumType)).toList().get(0);
    }


}
