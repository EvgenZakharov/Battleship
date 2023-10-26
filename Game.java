package battleship;

import java.util.Scanner;

public class Game {
    //methods
    public void changeTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }
    public void startGame() {

        int sankShipsPlayer1 = 0;
        int sankShipsPlayer2 = 0;

        Player player1 = new Player("Player1", new BattleArray());
        Player player2 = new Player("Player2", new BattleArray());

        //player1 ships setuping
        System.out.println("Player 1, place your ships to the game field");
        player1.battleArray.battleArrayPrinting();
        for (Ships ship : Ships.values()) {
            player1.battleArray.setupShips(ship);
        }
        changeTurn();

        //player2 ships setuping
        System.out.println("Player 2, place your ships to the game field");
        player2.battleArray.battleArrayPrinting();
        for (Ships ship : Ships.values()) {
            player2.battleArray.setupShips(ship);
        }
        changeTurn();

        Player playerInit = new Player("Player Initial", new BattleArray());

        while (true) {
            //player1 shot taking
            playerInit.battleArray.battleArrayPrinting();
            System.out.println("---------------------");
            player1.battleArray.battleArrayPrinting();
            System.out.println("Player 1, it's your turn:");
            sankShipsPlayer2 += player2.battleArray.shotTaking();
            if (sankShipsPlayer2 < Ships.values().length) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }
            changeTurn();

            //player2 shot taking
            playerInit.battleArray.battleArrayPrinting();
            System.out.println("---------------------");
            player2.battleArray.battleArrayPrinting();
            System.out.println("Player 2, it's your turn:");
            sankShipsPlayer1 += player1.battleArray.shotTaking();
            if (sankShipsPlayer1 < Ships.values().length) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }
            changeTurn();
        }
    }
}