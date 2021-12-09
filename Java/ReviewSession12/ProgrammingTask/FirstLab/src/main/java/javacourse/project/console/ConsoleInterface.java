package javacourse.project.console;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class ConsoleInterface {
    private Writer writer;
    private Reader reader;
    private Scanner scanner;
    private boolean interactive;
    public ConsoleInterface(Writer writer, Reader reader, boolean interactive) {
        this.writer = writer;
        this.reader = reader;
        this.scanner = new Scanner(reader);
        this.interactive = interactive;
    }
    public String read(){
        return scanner.nextLine();
    }

    public void write(String message){
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e){
            e.getMessage();
        }
    }
    public String readWithMessage(String message, boolean nullable){
        String text = "";
        if (!nullable){
            text =  " Данное значение не может быть null";
        }
        String line = "";
        do {
            if (interactive){
                write(message + text );
            }
            //write(message + text );
            line = scanner.nextLine();
            line = line.isEmpty()?null:line;
        }
        while (!nullable && line ==null);
        return line;
    }

    public boolean hasNextLine(){
        return scanner.hasNextLine();
    }

    public boolean isInteractive() {
        return interactive;
    }
}
    /*
TODO
   у меня есть класс ConsoleInterface с помощью которого я читаю данные ввода или вывожу 'подсказки' пользователю об ошибках.
   Я его использую при обычной работе с консолью и еще когда исполняется команда execute_script.
   Меня не устраивает что я слишком 'гоняю' экземпляр этого класса, постоянно куда то передавая.
   (Особенно это стремно выглядит в AskerArgument, но в целом в других местах не лучше)
   Я думал сделать какой нибудь синглтон  (и в приватном конструкторе сразу же задать writer и reader)
   и обращаться к методам этого класса примерно так
   ConsoleInterface.getInstance().read() или не знаю может методы статические сделать.
   но как правильно  использовать синглтон и нужно ли я не понимаю. Проблема в выполнении ExecuteScriptCommand.
   Там данные читаются с файла, и я  создаю новый объект используя FileReader. Был бы синглтон, я бы не смог задать FileReader
   Как здесь можно поступить? Просто как то не красиво, что  я постоянно туда сюда передаю это всё...или это норм
   Одним из решений я вижу:
   ConsoleInterface.getInstance().setReader();
   и в ExecuteScriptCommand
   сделать что то подобное
   ConsoleInterface.getInstance().setReader(new FileReader(pathToScript.toFile());
   после того как скрипт выполнится вернуть
   ConsoleInterface.getInstance().setReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
   _________
   второй вариант:
   не знаю нормально ли сделать методы этого класса статическими
   убрать конструктор
   сделать методы static setReader(Reader reader), setWriter(Writer writer)
   изначально я задам в методе main нужные мне ридеры и врайтеры, а в ExecuteScriptCommand поменяю ридер и потом верну
   По сути примерно одно и тоже.
*/