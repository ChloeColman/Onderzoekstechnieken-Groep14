package Domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {

    private int numberOfDecks;
    private List<Integer> shoe;
    private static final SecureRandom randomNumbers = new SecureRandom();
    private int location = -1;
    private int runningCount = 0;

    public Shoe() {
        this.shoe = new ArrayList<>();
    }
    
    public int getCount(){
        return runningCount;
    }

    public double getTrueCount() {
        double trueCount = (runningCount / (numberOfDecks - (Math.ceil((location + 1) / 52))) + 1);
        return trueCount;
    }

    private void updateCount(int card) {
        if (1 < card && card < 4) {
            runningCount++;
        } else if (3 < card && card < 7) {
            runningCount++;
            runningCount++;
        } else if (card == 7) {
            runningCount++;
        } else if (card == 10) {
            runningCount--;
            runningCount--;
        } else if (card == 1) {
            runningCount--;
        }
    }

    public void addDeck(Deck deck) {
        for (int card : deck.getDeck()) {
            shoe.add(card);
        }
    }

    public void shuffle() {
        Collections.shuffle(shoe, randomNumbers);
        location = -1;
        setNumberOfDecks();
    }

    public int deal() {
        if (location < shoe.size() - 1) {
            location++;
            updateCount(shoe.get(location));
            return shoe.get(location);
        } else {
            this.shuffle();
            location++;
            updateCount(shoe.get(location));
            return shoe.get(location);
        }
    }

    private void setNumberOfDecks() {
        numberOfDecks = shoe.size() / 52;
    }
}
