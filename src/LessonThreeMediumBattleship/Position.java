package LessonThreeMediumBattleship;
import java.util.Scanner;

class Position {
    private Coordinate start;
    private Coordinate stop;


    Position(String start, String stop) {
        Coordinate firstCoordinate = Coordinate.readInput(start);
        Coordinate secondCoordinate = Coordinate.readInput(stop);


        if (firstCoordinate.getX() < secondCoordinate.getX() || firstCoordinate.getY() < secondCoordinate.getY()) {
            this.start = firstCoordinate;
            this.stop = secondCoordinate;
        } else {
            this.start = secondCoordinate;
            this.stop = firstCoordinate;
        }
    }

    static Position readPosition() {
        Scanner scanner = new Scanner(System.in);
        String start = scanner.next();
        String stop = scanner.next();
        return new Position(start, stop);
    }


    public Coordinate getStart() {
        return start;
    }

    public Coordinate getStop() {
        return stop;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public void setStop(Coordinate stop) {
        this.stop = stop;
    }
}