package javacourse.project.console;
import javacourse.project.data.Color;
import javacourse.project.data.Coordinates;
import javacourse.project.data.Location;
import javacourse.project.data.Person;
import javacourse.project.exceptions.InvalidInputException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class AskerArgument {
    public Person readPerson(ConsoleInterface consoleManager) {
        String name = readName(consoleManager);
        Coordinates coordinates = readCoordinate(consoleManager);
        Long height = readHeight(consoleManager);
        LocalDateTime birthday = readBirthday(consoleManager);
        String passportID = readPassportId(consoleManager);
        Color hairColor = readColor(consoleManager);
        Location location = readLocation(consoleManager);

        return new Person(name, coordinates, height, birthday, passportID, hairColor, location);
    }

    public void updatePerson(Person person, ConsoleInterface consoleManager) {
        if (askQuestion("имя", consoleManager)) person.setName(readName(consoleManager));
        if (askQuestion("координаты", consoleManager)) person.setCoordinates(readCoordinate(consoleManager));
        if (askQuestion("рост", consoleManager)) person.setHeight(readHeight(consoleManager));
        if (askQuestion("дату рождения", consoleManager)) person.setBirthday(readBirthday(consoleManager));
        if (askQuestion("Паспорт ID", consoleManager)) person.setPassportID(readPassportId(consoleManager));
        if (askQuestion("цвет волос", consoleManager)) person.setHairColor(readColor(consoleManager));
        if (askQuestion("локацию", consoleManager)) person.setLocation(readLocation(consoleManager));

    }

    public Location readLocation(ConsoleInterface consoleManager) {
        double x, z;
        Double y;
        String name;

        while (true) {
            try {
                x = Double.parseDouble(consoleManager.readWithMessage("Введите координату X относительно локации", false));
                y = Double.parseDouble(consoleManager.readWithMessage("Введите координату Y относительно локации", false));
                z = Double.parseDouble(consoleManager.readWithMessage("Введите координату Z относительно локации", false));
                name = consoleManager.readWithMessage("Введите имя локации: ", false);
                break;

            } catch (NumberFormatException e) {
                consoleManager.write("Неправильный ввод. Координаты должны быть представлены числом и не могут являться null. Попробуйте зааново");
            }
        }
        return new Location(x, y, z, name);
    }

    private String readName(ConsoleInterface consoleManager) {
        return consoleManager.readWithMessage("Введите имя: ", false);
    }

    private Coordinates readCoordinate(ConsoleInterface consoleManager) throws NumberFormatException {
        Long x;
        Double y;

        while (true) {
            try {
                x = Long.parseLong(consoleManager.readWithMessage("Введите расположение координаты по X", false).trim());
                break;
            } catch (NumberFormatException e) {
                consoleManager.write("Координата X должна быть числом типа long. Оно не может являться null");
            }
        }
        while (true) {
            try {
                y = Double.parseDouble(consoleManager.readWithMessage("Введите расположение координаты по Y ", false).trim());
                if (y < -536) {
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                consoleManager.write("Координата Y должна быть числом типа float. Оно не может являться null и не больше -537");
            }
        }
        return new Coordinates(x, y);
    }


    private Long readHeight(ConsoleInterface consoleManager) {
        Long height = null;
        while (true) {
            try {
                String line = consoleManager.readWithMessage("Введите значение height. Оно должно быть больше 0", true);
                if (line != null) {
                    height = Long.parseLong(line.trim());
                    if (height <= 0) {
                        continue;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                consoleManager.write("Height должна быть числом типа long");
            }
        }
        return height;
    }

    private LocalDateTime readBirthday(ConsoleInterface consoleManager) {
        LocalDateTime localDateTime = null;
        while (true) {
            try {
                String time = consoleManager.readWithMessage("Введите дату дня рождения в таком формате (YYYY-MM-DD): ", true);
                if (time == null) {
                    break;
                }
                localDateTime = LocalDate.parse(time, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
                break;
            } catch (DateTimeParseException e) {
                consoleManager.write("Вы указали неверный формат даты");
            }
        }
        return localDateTime;
    }

    private Color readColor(ConsoleInterface consoleManager) {
        StringBuilder stringBuilder = new StringBuilder();
        String color;
        Arrays.stream(Color.values()).forEach(x -> stringBuilder.append(x.getColor()).append("\n"));
        if (consoleManager.isInteractive()) consoleManager.write(stringBuilder.toString());

        while (true) {
            try {
                color = consoleManager.readWithMessage("Введите один из предложенных цветов: ", false);
                if (!Color.isPresent(color)) {
                    throw new InvalidInputException();
                }
                break;
            } catch (InvalidInputException e) {
                consoleManager.write("Неверный ввод. Попробуйте еще раз");
            }
        }
        if (color == null) {
            return null;
        }
        return Color.getColorByString(color);
    }

    private String readPassportId(ConsoleInterface consoleManager) {
        return consoleManager.readWithMessage("Введите passport id", true);

    }

    private boolean askQuestion(String ask, ConsoleInterface consoleManager) {
        String question = String.format("Вы хотетие поменять у человека %s ? Введите + или -", ask);
        String answer;
        while (true) {
            answer = consoleManager.readWithMessage(question, false);
            if (answer.equals("+") || answer.equals("-")) {
                break;
            }
            consoleManager.write("Неправильный ввод");
        }
        return answer.equals("+");
    }
}
