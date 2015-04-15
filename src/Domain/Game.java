package Domain;

public class Game {

    private Player[] players;
    private Shoe shoe;
    private int upcard = 0;
    private Player dealer;

    public Game(Player[] players, Shoe shoe) {
        this.shoe = shoe;
        this.players = players;
        findDealer();
    }

    public void play() {
        for (Player currentPlayer : players) {
            currentPlayer.addCard(shoe.deal());
            currentPlayer.addCard(shoe.deal());
        }
        upcard = players[players.length - 1].getCards().get(0);
        for (Player currentPlayer : players) {
            currentPlayer.setFace(upcard);
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
