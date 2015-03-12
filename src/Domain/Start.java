package Domain;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {

    public static void main(String[] args) {
        while (true) {
            Scanner input = new Scanner(System.in);
            Player[] players;
            int oneOnOneInt = 0;
            boolean oneOnOne = false;
            int treshold = -100;

            while (!(oneOnOneInt == 1 | oneOnOneInt == 2)) {
                System.out.print("Type of Game (1 for player vs Dealer, 2 for free for all):");
                try {
                    oneOnOneInt = input.nextInt();
                } catch (Exception e) {
                    System.out.println("invalid input, please try again.");
                }
                if (oneOnOneInt == 1) {
                    oneOnOne = true;
                } else if (oneOnOneInt == 2) {
                    oneOnOne = false;
                } else {
                    System.out.println("invalid input, please try again.");
                }
            }
            System.out.print("number of decks: ");
            int NumberOfDecks = input.nextInt();

            System.out.print("number of games: ");
            int numberOfGames = input.nextInt();

            System.out.print("number of players, without dealer: ");
            int numberOfPlayers = input.nextInt();
            players = new Player[numberOfPlayers + 1];

            System.out.print("Strategy: \n1) pick treshold \n2) go for blackjack \n3) Never Hit \n4)basic Strategy \n420) default (hit to 17)\n");
            int strategy = input.nextInt();

            if (strategy == 1) {
                for (int i = 1; i <= numberOfPlayers; i++) {
                    treshold = -100;
                    while (!(treshold >= -21 && treshold <= 21)) {
                        System.out.print("Player Treshold or negative for random starting from number: ");
                        try {
                            treshold = input.nextInt();
                        } catch (Exception e) {
                            System.out.println("invalid input, please try again.");
                        }
                        if (treshold >= -21 && treshold <= 21) {
                            players[i - 1] = new Player(i, treshold, strategy);
                        } else {
                            System.out.println("invalid input, please try again.");
                        }
                    }
                }
            } else {
                for (int i = 1; i <= numberOfPlayers; i++) {
                    players[i - 1] = new Player(i, treshold, strategy);
                }
            }

            players[numberOfPlayers] = new Player(numberOfPlayers + 1, 17, strategy, true);
            Shoe shoe = new Shoe(NumberOfDecks);
            for (int i = 1;
                    i <= numberOfGames;
                    i++) {
                Game game = new Game(players, shoe, oneOnOne);
                game.play();
            }

            for (Player currentPlayer : players) {
                if (!(currentPlayer.isDealer())) {
                    System.out.printf("player %d wins: %d games played %d win ratio: %.2f%n", currentPlayer.getID(), currentPlayer.getWins(), numberOfGames, (float) currentPlayer.getWins() / numberOfGames);
                } else {
                    if (oneOnOne) {
                        System.out.printf("dealer wins: %d games played %d win ratio: %.2f%n", currentPlayer.getWins(), numberOfGames * numberOfPlayers, (float) currentPlayer.getWins() / (numberOfGames * numberOfPlayers));
                    } else {
                        System.out.printf("dealer wins: %d games played %d win ratio: %.2f%n", currentPlayer.getWins(), numberOfGames, (float) currentPlayer.getWins() / numberOfGames);
                    }
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }
}
