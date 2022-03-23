package LessonThreeMediumBattleship;
import java.util.Scanner;

class Coordinate {
    final int x;
    final int y;

    Coordinate(String input) throws WrongLocationException {
        int x = input.endsWith("10") ? 9 : input.charAt(1) - 49;
        int y = input.charAt(0) - 65;

        if (x > 9 || y < 0 || y > 9 || (input.length() == 3 && !input.endsWith("10"))) {
            throw new WrongLocationException();
        }

        this.x = x;
        this.y = y;
    }

    static Coordinate readCoordinate() throws WrongLocationException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        return new Coordinate(input);
    }
}