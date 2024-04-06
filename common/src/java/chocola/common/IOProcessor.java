package chocola.common;

import chocola.interfaces.Point;
import chocola.interfaces.Validator;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Function;

public class IOProcessor  {

    private static final Scanner SC = new Scanner(System.in);
    private static final PrintStream PS = System.out;

    private IOProcessor() {
    }

    public static String readLine() {
        return SC.nextLine();
    }

    public static String readLine(Validator<String> validator) throws InvalidInputException {
        String line = readLine();
        if (validator.isValid(line)) return line;

        throw new InvalidInputException();
    }

    public static Point readLine(Validator<String> validator, Function<String, Point> mapper) throws InvalidInputException {
        String line = readLine(validator);
        return mapper.apply(line);
    }

    public static void printStartMessage() {
        println("게임을 시작합니다.");
    }

    public static void printEndMessage() {
        println("게임을 종료합니다.");
    }

    public static void printDrawMessage() {
        println("무승부로 게임을 종료합니다.");
    }

    public static void println(String message) {
        PS.println(message);
    }

    public static void print(String message) {
        PS.print(message);
    }
}
