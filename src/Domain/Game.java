package Domain;

public class Game {

    private Player[] players;
    private Shoe shoe;
    private boolean oneOnOne;
    private int face=0;

    public Game(Player[] players, Shoe shoe, boolean oneOnOne) {
        this.shoe = shoe;
        this.players = players;
        this.oneOnOne = oneOnOne;
    }

    public void play() {
        for (Player currentPlayer : players) {
            currentPlayer.addCard(shoe.deal());
            currentPlayer.addCard(shoe.deal());
        }
        face=players[players.length-1].getCards().get(0);
        for (Player currentPlayer : players) {
            currentPlayer.setFace(face);
            while (currentPlayer.wantCard()) {
                currentPlayer.addCard(shoe.deal());
            }
        }
        if (oneOnOne) {
            pickWinnerOneOnOne();
        } else {
            pickWinnerAll();
        }
        nextRound();
    }

    private void pickWinnerOneOnOne() {
        Player dealer = null;
        for (Player currentPlayer : players) {

            if (currentPlayer.isDealer()) {
                dealer = currentPlayer;
            }
        }

        for (Player currentPlayer : players) {
            if (!currentPlayer.isDealer()) {
                if (dealer.getCardTotal() >= currentPlayer.getCardTotal()) {
                    dealer.isWinrar();
                } else {
                    currentPlayer.isWinrar();
                }
            }
        }
    }

    private void pickWinnerAll() {
        Boolean foundWinner = false;
        for (Player currentPlayer : players) {
            if (currentPlayer.getCardTotal() <= 21) {
                foundWinner = true;
            }
        }
        if (foundWinner) {
            int high = 0;
            for (Player currentPlayer : players) {
                if (currentPlayer.getCardTotal() <= 21 && currentPlayer.getCardTotal() > high) {
                    high = currentPlayer.getCardTotal();
                }
            }
            int count = 0;
            for (Player currentPlayer : players) {
                if (currentPlayer.getCardTotal() == high) {
                    count++;
                }
            }
            if (count > 1) {
                for (Player currentPlayer : players) {
                    if (currentPlayer.getCardTotal() == high && currentPlayer.isDealer()) {
                        currentPlayer.isWinrar();
                        return;
                    }
                }
                for (Player currentPlayer : players) {
                    if (currentPlayer.getCardTotal() == high) {
                        currentPlayer.isWinrar();
                    }
                }
            } else {
                for (Player currentPlayer : players) {
                    if (currentPlayer.getCardTotal() == high) {
                        currentPlayer.isWinrar();
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
}
