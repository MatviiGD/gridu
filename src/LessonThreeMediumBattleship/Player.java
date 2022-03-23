package LessonThreeMediumBattleship;
import java.util.Arrays;

class Player {
    final String name;
    final String[][] board = new String[10][];
    final String[][] opponent_view = new String[10][];
    final String[] ROW_KEYS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    final Ship[] SHIPS = {
            new Ship("Aircraft Carrier", 5),
            new Ship("Battleship", 4),
            new Ship("Submarine", 3),
            new Ship("Cruiser", 3),
            new Ship("Destroyer", 2)
    };

    Player(String name) {
        this.name = name;

        for (int y = 0; y < 10; y++) {
            String[] row = new String[10];
            Arrays.fill(row, "~");
            board[y] = row;
        }

        for (int y = 0; y < 10; y++) {
            String[] row = new String[10];
            Arrays.fill(row, "~");
            opponent_view[y] = row;
        }
    }

    void printBoard(String[][] board) {
        System.out.print(" ");
        for (int x = 1; x < 11; x++) {
            System.out.print(" " + x);
        }
        System.out.println();

        for (int y = 0; y < 10; y++) {
            System.out.print(ROW_KEYS[y]);
            for (int x = 0; x < 10; x++) {
                String cell = board[y][x];
                System.out.print(" " + cell);
            }
            System.out.println();
        }
        System.out.println();
    }

    void printBoards() {
        printBoard(opponent_view);
        System.out.println("---------------------");
        printBoard(board);
    }

    private boolean isValidSize(Position position, int size) {
        if (position.start.x == position.stop.x) {
            return size == position.stop.y - position.start.y + 1;
        } else if (position.start.y == position.stop.y) {
            return size == position.stop.x - position.start.x + 1;
        } else {
            return false;
        }
    }

    private boolean areCollisions(Position position, int size) {
        int start_x = position.start.x;
        int start_y = position.start.y;
        int stop_x = position.stop.x;
        int stop_y = position.stop.y;

        assert start_x == stop_x || start_y == stop_y;
        assert stop_x - start_x + 1 == size || stop_y - start_y + 1 == size;

        if (start_x == stop_x) {
            for (int y = start_y; y != stop_y + 1; y++) {
                if (hasNeighbors(start_x, y)) {
                    return false;
                }
            }
        } else {
            for (int x = start_x; x != stop_x + 1; x++) {
                if (hasNeighbors(x, start_y)) {
                    return false;
                }
            }
        }

        return true;
    }


    private boolean hasNeighbors(int x, int y) {
        Shift[] shifts = {
                new Shift(-1, 1),
                new Shift(0, 1),
                new Shift(1, 1),
                new Shift(1, 0),
                new Shift(1, -1),
                new Shift(0, -1),
                new Shift(-1, -1),
                new Shift(-1, 0)
        };

        for (Shift shift : shifts) {
            try {
                if (board[y + shift.y][x + shift.x].equals("O")) {
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }

        return false;
    }

    void placeShip(int size) throws WrongLocationException, TooCloseException, WrongLengthException {
        Position position = Position.readPosition();

        if (!isValidSize(position, size)) {
            throw new WrongLengthException();
        }

        if (!areCollisions(position, size)) {
            throw new TooCloseException();
        }

        int start_x = position.start.x;
        int start_y = position.start.y;
        int stop_x = position.stop.x;
        int stop_y = position.stop.y;

        assert start_x == stop_x || start_y == stop_y;
        assert stop_x - start_x + 1 == size || stop_y - start_y + 1 == size;

        if (start_x == stop_x) {
            for (int y = start_y; y != stop_y + 1; y++) {
                board[y][start_x] = "O";
            }
        } else {
            for (int x = start_x; x != stop_x + 1; x++) {
                board[start_y][x] = "O";
            }
        }
    }

    void placeShips() {
        System.out.println(name + ", place your ships on the game field");
        System.out.println();
        printBoard(board);

        for (Ship ship : SHIPS) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", ship.name, ship.size);
            System.out.println();
            System.out.println();

            while (true) {
                try {
                    placeShip(ship.size);
                    break;
                } catch (WrongLengthException e) {
                    System.out.println();
                    System.out.printf("Error! Wrong length of the %s! Try again:", ship.name);
                    System.out.println();
                } catch (WrongLocationException e) {
                    System.out.println();
                    System.out.println("Error! Wrong ship location! Try again:");
                } catch (TooCloseException e) {
                    System.out.println();
                    System.out.println("Error! You placed it too close to another one. Try again:");
                }
                System.out.println();
            }

            System.out.println();
            printBoard(board);
        }
    }

    Coordinate fire() {
        Coordinate coordinate;

        printBoards();
        System.out.println(name + ", it's your turn:");
        System.out.println();


        while (true) {
            try {
                coordinate = Coordinate.readCoordinate();
                break;
            } catch (WrongLocationException e) {
                System.out.println();
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                System.out.println();
            }
        }

        System.out.println();

        return coordinate;
    }

    boolean shipIsStillAfloat(Coordinate coordinate) {
        return hasNeighbors(coordinate.x, coordinate.y);
    }

    public boolean hasShips() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (board[y][x].equals("O")) {
                    return true;
                }
            }
        }
        return false;
    }
}