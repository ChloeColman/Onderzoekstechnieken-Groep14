package Domain;

import java.util.List;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {

    public static void main(String[] args) {
        while (true) {

            Scanner input = new Scanner(System.in);
            Player[] players;
            int treshold = -100;
            boolean useChips;

            System.out.print("Sample Size: ");
            int dataPoints = input.nextInt();

            System.out.print("number of games: ");
            int numberOfGames = input.nextInt();

            System.out.print("press 1 to use chips instead of win/lose: ");
            useChips = input.nextInt() == 1;

            System.out.print("number of decks: ");
            int NumberOfDecks = input.nextInt();

            Deck deck = new Deck();
            Shoe shoe = new Shoe();
            for (int i = 0; i < NumberOfDecks; i++) {
                shoe.addDeck(deck);
            }
            shoe.shuffle();

            System.out.print("number of players: ");
            int numberOfPlayers = input.nextInt();
            players = new Player[numberOfPlayers + 1];

            System.out.print("Strategy: \n1) pick treshold \n2) go for blackjack \n3) Never Hit \n4) basic Strategy \n5) learning Strategy \n6) Advanced Learning Strategy \n0) default (hit to 17)\n");
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
                        players[0] = new Player(0, treshold, strategy, shoe);
                        for (int i = 1; i <= (numberOfPlayers - 1); i++) {
                            players[i] = new Player(i, 20, shoe);
                        }
                    } else {
                        System.out.println("invalid input, please try again.");
                    }
                }
            } else {
                players[0] = new Player(0, strategy, shoe);
                for (int i = 1; i <= (numberOfPlayers - 1); i++) {
                    players[i] = new Player(i, 20, shoe);
                }
            }

            players[numberOfPlayers] = new Player(numberOfPlayers, true);

            List<Integer> data = new ArrayList<>();
            List<Double> chipData = new ArrayList<>();
            for (int i = 1; i <= numberOfGames; i++) {
                Game game = new Game(players, shoe, useChips);
                game.play();
                if (i % (numberOfGames / dataPoints) == 0) {
                    data.add(players[0].getWins());
                    if (useChips) {
                        chipData.add(players[0].getChips());
                    }
                }
            }

            int i = 0;
            int place;
            if (useChips) {
                try {
                    FileWriter writer = new FileWriter("win-" + dataPoints + "-" + strategy + ".csv");
                    writer.append("#Games played, #games won, #chips won, \n");
                    for (int dataPoint : data) {
                        place = i * (numberOfGames / dataPoints);
                        writer.append(Integer.toString(place) + ", " + Integer.toString(dataPoint) + ", " + Double.toString(chipData.get(i)) + ", \n");
                        i++;
                    }
                    writer.append(Integer.toString(dataPoints) + ", " + Integer.toString(numberOfGames));

                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FileWriter writer = new FileWriter("win-" + dataPoints + "-" + strategy + ".csv");
                    writer.append("#Games played, #games won, \n");
                    for (int dataPoint : data) {
                        place = i * (numberOfGames / dataPoints);
                        i++;
                        writer.append(Integer.toString(place) + ", " + Integer.toString(dataPoint) + ", \n");
                    }
                    writer.append(Integer.toString(dataPoints) + ", " + Integer.toString(numberOfGames));

                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (strategy == 5) {
                try {
                    FileWriter writer = new FileWriter("decision tree - Learning.csv");
                    StrategyLearning learnStrat = (StrategyLearning) players[0].strat;
                    Set<Integer> keySet = learnStrat.map.keySet();
                    writer.append("cardTotal, Alpha(true), Alpha(false), \n");
                    for (int key : keySet) {
                        writer.append(Integer.toString(key) + ", "
                                + Double.toString(learnStrat.map.get(key).get(true)) + ", "
                                + Double.toString(learnStrat.map.get(key).get(false)) + ", \n");
                    }

                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (strategy == 6) {
                try {
                    FileWriter writer = new FileWriter("decision tree - Advanced Learning.csv");
                    StrategyAdvancedLearning advLearnStrat = (StrategyAdvancedLearning) players[0].strat;
                    Set<Integer> keySet = advLearnStrat.map.keySet();
                    writer.append("cardTotal, count, Alpha(true), Alpha(false), \n");
                    for (Integer key : keySet){
                        Set<Integer> keySetTwo = advLearnStrat.map.get(key).keySet();
                        for (Integer keyTwo : keySetTwo){
                            writer.append(Integer.toString(key) + ", " + 
                                    Integer.toString(keyTwo) + ", " + 
                                    Double.toString(advLearnStrat.map.get(key).get(keyTwo).get(true)) + ", " +  
                                    Double.toString(advLearnStrat.map.get(key).get(keyTwo).get(false)) + ", \n");
                        }
                    }

                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (useChips) {
                for (Player currentPlayer : players) {
                    if (!(currentPlayer.isDealer())) {
                        System.out.printf(Locale.ENGLISH, "player %d wins: %d games played %d win ratio: %.2f%% chips: %.1f%n", currentPlayer.getID(), currentPlayer.getWins(), numberOfGames, ((float) currentPlayer.getWins() / numberOfGames) * 100, currentPlayer.getChips());
                    } else {
                        System.out.printf(Locale.ENGLISH, "dealer %d wins: %d games played %d win ratio: %.2f%%%n", currentPlayer.getID(), currentPlayer.getWins(), numberOfGames, ((float) currentPlayer.getWins() / (numberOfGames * numberOfPlayers)) * 100);
                        try {
                            System.in.read();
                        } catch (IOException ex) {
                            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } else {
                for (Player currentPlayer : players) {
                    if (!(currentPlayer.isDealer())) {
                        System.out.printf(Locale.ENGLISH, "player %d wins: %d games played %d win ratio: %.2f%%%n", currentPlayer.getID(), currentPlayer.getWins(), numberOfGames, ((float) currentPlayer.getWins() / numberOfGames) * 100);
                    } else {
                        System.out.printf(Locale.ENGLISH, "dealer %d wins: %d games played %d win ratio: %.2f%%%n", currentPlayer.getID(), currentPlayer.getWins(), numberOfGames, ((float) currentPlayer.getWins() / (numberOfGames * numberOfPlayers)) * 100);
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
}
