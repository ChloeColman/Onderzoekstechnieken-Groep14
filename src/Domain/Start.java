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
            int treshold = -100;

            System.out.print("number of games: ");
            int numberOfGames = input.nextInt();

            System.out.print("number of decks: ");
            int NumberOfDecks = input.nextInt();

            System.out.print("number of players: ");
            int numberOfPlayers = input.nextInt();
            players = new Player[numberOfPlayers + 1];

            System.out.print("Strategy: \n1) pick treshold \n2) go for blackjack \n3) Never Hit \n4) basic Strategy \n5) learning Strategy \n420) default (hit to 17)\n");
            int strategy = input.nextInt();

            if (strategy == 1) {

                treshold = -100;
                while (!(treshold >= -21 && treshold <= 21)) {
                    System.out.print("Player Treshold or negative for random starting from number: ");
                    try {
                        treshold = input.nextInt();
                    } catch (Exception e) {
                        System.out.println("invalid input, please try again.");
                    }
                    if (treshold >= -21 && treshold <= 21) {
                        players[0] = new Player(0, treshold, strategy);
                        for (int i = 1; i <= (numberOfPlayers - 1); i++) {
                            players[i] = new Player(i, 20);
                        }
                    } else {
                        System.out.println("invalid input, please try again.");
                    }
                }
            } else {
                players[0] = new Player(0, strategy);
                for (int i = 1; i <= (numberOfPlayers - 1); i++) {
                    players[i] = new Player(i, 20);
                }
            }

            players[numberOfPlayers] = new Player(numberOfPlayers, true);
            Deck deck = new Deck();
            Shoe shoe = new Shoe();
            for (int i = 0; i < NumberOfDecks; i++) {
                shoe.addDeck(deck);
            }
            shoe.shuffle();

            for (int i = 1; i <= numberOfGames; i++) {
                Game game = new Game(players, shoe);
                game.play();
            }
            
            for (Player currentPlayer : players) {
                if (!(currentPlayer.isDealer())) {
                    System.out.printf("player %d wins: %d games played %d win ratio: %.2f%%%n", currentPlayer.getID(), currentPlayer.getWins(), numberOfGames, ((float) currentPlayer.getWins() / numberOfGames) * 100);
                } else {
                    System.out.printf("dealer %d wins: %d games played %d win ratio: %.2f%%%n", currentPlayer.getID(), currentPlayer.getWins(), numberOfGames, ((float) currentPlayer.getWins() / (numberOfGames * numberOfPlayers)) * 100);
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
