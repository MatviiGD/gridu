package LessonThreeMediumBattleship;

import java.util.Scanner;

class Coordinate {
    private int x;
    private int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public static Coordinate readInput(String input) {

        int x = input.endsWith("10") ? 9 : input.charAt(1) - 49;
        int y = input.charAt(0) - 65;
        checkCoordinate(input, x, y);
        return new Coordinate(x, y);
    }

    public static Coordinate readCoordinate() throws WrongLocationException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        return readInput(input);
    }

    private static void checkCoordinate(String input, int x, int y) {
        if ((x > 9 || y < 0 || y > 9) || (input.length() == 3 && !input.endsWith("10"))) {
            try {
                throw new WrongLocationException("Error! Wrong ship location! Try again:");
            } catch (WrongLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}