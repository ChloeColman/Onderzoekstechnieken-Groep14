package Domain;

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
                    if (currentPlayer.strat instanceof LearningStrategy) {
                        currentPlayer.strat.eval(currentPlayer);
                    }
                }
            }
            pickWinnerChips();
            pickWinner();
            nextRound();
        } else {
            for (Player currentPlayer : players) {
                currentPlayer.addCard(shoe.deal());
                currentPlayer.addCard(shoe.deal());
            }
            for (Player currentPlayer : players) {
                while (currentPlayer.wantCard()) {
                    currentPlayer.addCard(shoe.deal());
                    if (currentPlayer.strat instanceof LearningStrategy) {
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

                if (currentPlayer.getCardTotal() == 21 && currentPlayer.getNumberOfCards() == 2) {
                    currentPlayer.addChips((double) currentPlayer.getBet() + (double) currentPlayer.getBet()* 1.5);

                } else if (currentPlayer.getCardTotal() > dealer.getCardTotal()) {
                    currentPlayer.addChips(currentPlayer.getBet() * 2);

                } else if (currentPlayer.getCardTotal() < dealer.getCardTotal()) {
                    currentPlayer.loseChips();

                } else if (dealer.getCardTotal() == currentPlayer.getCardTotal()) {
                    currentPlayer.addChips(currentPlayer.getBet());

                } else {
                    throw new UnknownError();
                }
            }
        }
    }

    public void pickWinner() {
        if (dealer.getCardTotal() == 21) {
            for (int i = 1; i < players.length; i++) {
                dealer.isWinner();
            }
        } else {
            for (Player currentPlayer : players) {
                if (!currentPlayer.isDealer()) {
                    if (currentPlayer.getCardTotal() > 21) {
                        dealer.isWinner();
                    } else if (dealer.getCardTotal() > 21) {
                        currentPlayer.isWinner();
                    } else {
                        if (currentPlayer.getCardTotal() > dealer.getCardTotal()) {
                            currentPlayer.isWinner();
                        } else {
                            dealer.isWinner();
                        }
                    }
                }
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
