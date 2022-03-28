package LessonThreeMediumBattleship;
import java.util.Scanner;

class Game {
 private final Player player1;
 private final Player player2;

    Game() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
    }

   private void passTurn() {
     System.out.println("The game starts!");
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        System.out.println("Player 2, place your ships to the game field");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public  void placeShips() {
        player1.placeShips();
        passTurn();

        player2.placeShips();
        passTurn();
    }

    private void fire(Player fromPlayer, Player toPlayer) {
        Coordinate coordinate = fromPlayer.fire();

        if (!toPlayer.board[coordinate.getY()][coordinate.getX()].equals("~")) {
            toPlayer.board[coordinate.getY()][coordinate.getX()] = "X";
            fromPlayer.opponent_view[coordinate.getY()][coordinate.getX()] = "X";

            if (toPlayer.shipIsStillAfloat(coordinate)) {
                System.out.println("You hit a ship!");
            } else if (toPlayer.hasShips()) {
                System.out.println("You sank a ship!");
            } else {
                System.out.print("You sank the last ship. You won. Congratulations!");
                System.exit(0);
            }
        } else {
            toPlayer.board[coordinate.getY()][coordinate.getX()] = "M";
            fromPlayer.opponent_view[coordinate.getY()][coordinate.getX()] = "M";
            System.out.println("You missed!");
        }
    }

    public void play() {
        while (player1.hasShips() || player2.hasShips()) {
            fire(player1, player2);
            passTurn();
            fire(player2, player1);
            passTurn();
        }
    }
}