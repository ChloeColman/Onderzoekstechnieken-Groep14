package Domain;

import java.util.List;

public class Game {

    private Player[] players;
    private Shoe shoe;
    private Player dealer;
    boolean useChips;

    public Game(Player[] players, Shoe shoe, boolean useChips) {
        this.shoe = shoe;
        this.players = players;
        findDealer();
        this.useChips = useChips;
    }

    public void play() {
        if (useChips) {
            for (Player currentPlayer : players) {
                currentPlayer.addCard(shoe.deal());
                currentPlayer.addCard(shoe.deal());
                currentPlayer.setBet(shoe);
            }
            for (Player currentPlayer : players) {
                while (currentPlayer.wantCard()) {
                    currentPlayer.addCard(shoe.deal());
                    if (currentPlayer.strat instanceof StrategyLearning || currentPlayer.strat instanceof StrategyAdvancedLearning) {
                        currentPlayer.strat.eval(currentPlayer);
                    }
                }
            }
            pickWinnerChips();
            pickWinner();
            /*   for (Player currentPlayer : players) {
             System.out.print("Player " + currentPlayer.getID() + " => ");
             System.out.print(" = " + currentPlayer.getCardTotal() + " || ");
             }
             System.out.println("");*/
            nextRound();
        } else {
            for (Player currentPlayer : players) {
                currentPlayer.addCard(shoe.deal());
                currentPlayer.addCard(shoe.deal());
            }
            for (Player currentPlayer : players) {
                while (currentPlayer.wantCard()) {
                    currentPlayer.addCard(shoe.deal());
                    if (currentPlayer.strat instanceof StrategyLearning || currentPlayer.strat instanceof StrategyAdvancedLearning) {
                        currentPlayer.strat.eval(currentPlayer);
                    }
                }
            }
            pickWinner();
            nextRound();
        }
    }

    public void pickWinnerChips() {
        for (Player currentPlayer : players) {
            if (!currentPlayer.isDealer()) {

                if (currentPlayer.getCardTotal() == 21 && currentPlayer.getNumberOfCards() == 2 && !(dealer.getCardTotal() == 21 && dealer.getCards().size() == 2)) {
                    currentPlayer.addChips((double) currentPlayer.getBet() * 2.5);

                } else if (currentPlayer.getCardTotal() > dealer.getCardTotal() && currentPlayer.getCardTotal() <= 21) {
                    currentPlayer.addChips(currentPlayer.getBet() * 2);

                } else if (currentPlayer.getCardTotal() < dealer.getCardTotal() && dealer.getCardTotal() <= 21) {
                    currentPlayer.loseChips();
                    
                } else if (currentPlayer.getCardTotal() > 21) {
                    currentPlayer.loseChips();
                    
                } else if (dealer.getCardTotal() > 21){
                    currentPlayer.addChips(currentPlayer.getBet()*2);
                    
                } else if (dealer.getCardTotal() == currentPlayer.getCardTotal()) {
                    currentPlayer.addChips(currentPlayer.getBet());

                } else {
                    throw new UnknownError();
                }
            }
        }
    }

    public void pickWinner() {
        for (Player currentPlayer : players) {
            if (!currentPlayer.isDealer()) {
                if (dealer.getCardTotal() == 21 && dealer.getCards().size() == 2) {
                    dealer.isWinner();
                } else if (currentPlayer.getCardTotal() > 21) {
                    dealer.isWinner();
                } else if (dealer.getCardTotal() > 21) {
                    currentPlayer.isWinner();
                } else if (dealer.getCardTotal() >= currentPlayer.getCardTotal()) {
                    dealer.isWinner();
                } else if (dealer.getCardTotal() < currentPlayer.getCardTotal()) {
                    currentPlayer.isWinner();
                } else {
                    throw new UnknownError();
                }
//                 if (currentPlayer.getCardTotal() > 21 || dealer.getCardTotal() == 21) {
//                 dealer.isWinner();
//                 } else if (dealer.getCardTotal() > 21 || currentPlayer.getCardTotal() > dealer.getCardTotal()) {
//                 currentPlayer.isWinner();
//                 } else {
//                 dealer.isWinner();
//                 }
            }
        }
    }

    private void nextRound() {
        for (Player currentPlayer : players) {
            currentPlayer.nextRound();
        }
    }

    private void findDealer() {
        for (Player currentplayer : players) {
            if (currentplayer.isDealer()) {
                dealer = currentplayer;
            }
        }
    }
}
