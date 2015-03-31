package Domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Player {

    StrategyInterface strat;
    private int ID;
    private List<Integer> cards = new ArrayList<>();
    private int sum;
    private int treshold;
    private int wins = 0;
    private int face = 0;
    private boolean isDealer = false;
    private static final SecureRandom randomNumber = new SecureRandom();

    public Player(int ID, int strategy) {
        this.ID = ID;
        setStrategy(strategy);
    }

    public Player(int ID, int treshold, int strategy) {
        this.ID = ID;
        this.treshold = treshold;
        setStrategy(strategy);
    }

    public Player(int ID, boolean isDealer) {
        this.ID = ID;
        this.isDealer = isDealer;
        setStrategy(20);
    }

    public void setStrategy(int strategy) {
        if (!isDealer()) {
            switch (strategy) {
                case 1:
                    this.strat = new TresholdStrategy();
                    break;
                case 2:
                    this.strat = new GoForBlackjackStrategy();
                    break;
                case 3:
                    this.strat = new NeverHitStrategy();
                    break;
                case 4:
                    this.strat = new BasicStrategy();
                    break;
                default:
                    this.strat = new DefaultStrategy();
                    break;
            }
        } else {
            this.strat = new DefaultStrategy();
        }
    }

    public int getTreshold() {
        int treshReturn;
        if (treshold < 0) {
            treshReturn = Math.abs(treshold) + randomNumber.nextInt(21 - treshold);
        } else {
            treshReturn = treshold;
        }
        return treshReturn;
    }

    public List<Integer> getCards() {
        return cards;
    }

    public boolean wantCard() {
        return strat.wantCard(this);
    }

    public void addCard(int card) {
        cards.add(card);
    }

    public int getCardTotal() {
        sum = 0;
        for (int waarde : cards) {
            if (waarde == 1) {
                sum += 11;
            } else {
                sum += waarde;
            }
        }
        if (sum > 21) {
            sum = 0;
            for (int waarde : cards) {
                sum += waarde;
            }
            return sum;
        } else {
            return sum;
        }
    }

    public void nextRound() {
        sum = 0;
        cards.clear();
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void isWinrar() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public int getFace() {
        return face;
    }

    public int getID() {
        return ID;
    }

}
