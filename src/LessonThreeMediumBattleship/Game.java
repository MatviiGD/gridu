package LessonThreeMediumBattleship;
import java.util.Scanner;

class Game {
    Player player1;
    Player player2;

    Game() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
    }

    void passTurn() {
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    void placeShips() {
        player1.placeShips();
        passTurn();

        player2.placeShips();
        passTurn();
    }

    void fire(Player fromPlayer, Player toPlayer) {
        Coordinate coordinate = fromPlayer.fire();

        if (!toPlayer.board[coordinate.y][coordinate.x].equals("~")) {
            toPlayer.board[coordinate.y][coordinate.x] = "X";
            fromPlayer.opponent_view[coordinate.y][coordinate.x] = "X";

            if (toPlayer.shipIsStillAfloat(coordinate)) {
                System.out.println("You hit a ship!");
            } else if (toPlayer.hasShips()) {
                System.out.println("You sank a ship!");
            } else {
                System.out.print("You sank the last ship. You won. Congratulations!");
                System.exit(0);
            }
        } else {
            toPlayer.board[coordinate.y][coordinate.x] = "M";
            fromPlayer.opponent_view[coordinate.y][coordinate.x] = "M";
            System.out.println("You missed!");
        }
    }

    void play() {
        while (player1.hasShips() || player2.hasShips()) {
            fire(player1, player2);
            passTurn();
            fire(player2, player1);
            passTurn();
        }
    }
}