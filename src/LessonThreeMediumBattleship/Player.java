package LessonThreeMediumBattleship;
import java.util.Arrays;

class Player {
    private String name;
    private String[][] board = new String[10][];
    private String[][] opponent_view = new String[10][];
    private String[] ROW_KEYS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private Ship[] SHIPS = {
            new Ship("Aircraft Carrier", 5),
            new Ship("Battleship", 4),
            new Ship("Submarine", 3),
            new Ship("Cruiser", 3),
            new Ship("Destroyer", 2)
    };

    Player(String name) {
        this.name = name;
        this.fillBoard();
        this.fillOpponentView();

    }

    public void fillBoard() {
        for (int y = 0; y < 10; y++) {
            String[] row = new String[10];
            Arrays.fill(row, "~");
            board[y] = row;
        }
    }

    public void fillOpponentView() {
        for (int y = 0; y < 10; y++) {
            String[] row = new String[10];
            Arrays.fill(row, "~");
            opponent_view[y] = row;
        }

    }

    private void printBoard(String[][] board) {
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

    private void printBoards() {
        printBoard(opponent_view);
        System.out.println("---------------------");
        printBoard(board);
    }

    private boolean isValidSize(Position position, int size) {
        if (position.getStart().getX() == position.getStop().getX()) {
            return size == position.getStop().getY() - position.getStart().getY() + 1;
        } else if (position.getStart().getY() == position.getStop().getY()) {
            return size == position.getStop().getX() - position.getStart().getX() + 1;
        } else {
            return false;
        }
    }

    private boolean areCollisions(Position position, int size) {
        int start_x = position.getStart().getX();
        int start_y = position.getStart().getY();
        int stop_x = position.getStop().getX();
        int stop_y = position.getStop().getY();


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
            } catch (ArrayIndexOutOfBoundsException ignored) {
                System.out.println("you so close");
            }
        }

        return false;
    }

    public void placeShip(int size) throws WrongLocationException, TooCloseException, WrongLengthException {
        Position position = Position.readPosition();

        if (!isValidSize(position, size)) {
            throw new WrongLengthException("Error! Wrong length of the Submarine! Try again:");
        }

        if (!areCollisions(position, size)) {
            throw new TooCloseException("Error! You placed it too close to another ship.Try again:");
        }

        int start_x = position.getStart().getX();
        int start_y = position.getStart().getY();
        int stop_x = position.getStop().getX();
        int stop_y = position.getStop().getY();


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

    public void placeShips() {
        System.out.println(name + ", place your ships on the game field");
        System.out.println();
        printBoard(board);

        for (Ship ship : SHIPS) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", ship.name, ship.size);
            System.out.println();
            System.out.println();
            //1)коли без ВАЙЛУ і  користувач  вводить не правильні дані ловиться кетч і береться наступний корабель
            //2)коли є ВАЙЛ користувач  ставить той корабель стільки, скільки треба до поки не поставить без помилки
            //3)бо якщо без вайлу я пропусаю той корабель і забираю можливість у користувача,
            // гравця ввести ще раз коректні дані для розміщення корабля

            while (true) {
                try {
                    placeShip(ship.size);//якщо в цьому методі помилка не правильно поставив зловився кетч
                    break;//якщо в методі placeShip буде без помилок  ми перейдемо в рядок 178 і далі по рядку
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
            }
            System.out.println();

            System.out.println();
            printBoard(board);// на друкувало розміщений корабель тобто той вірний
            // і пішло взяло наступний корабель якщо без помилки
        }
    }

    public Coordinate fire() {
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

    public boolean shipIsStillAfloat(Coordinate coordinate) {
        return hasNeighbors(coordinate.getX(), coordinate.getY());
    }

    public boolean hasShips() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if ("O".equals(board[y][x])) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String[][] getOpponent_view() {
        return opponent_view;
    }

    public void setOpponent_view(String[][] opponent_view) {
        this.opponent_view = opponent_view;
    }

    public String[] getROW_KEYS() {
        return ROW_KEYS;
    }

    public void setROW_KEYS(String[] ROW_KEYS) {
        this.ROW_KEYS = ROW_KEYS;
    }

    public Ship[] getSHIPS() {
        return SHIPS;
    }

    public void setSHIPS(Ship[] SHIPS) {
        this.SHIPS = SHIPS;
    }
}