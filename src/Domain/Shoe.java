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
    private int count = 0;

    public Shoe() {
        this.shoe = new ArrayList<>();
    }

    public int getTrueCount() {
        return (int) (count / (Math.ceil((location + 1) / 52)));
    }

    private void updateCount(int card) {
        if (card > 1 && card < 7) {
            count++;
        } else if (card == 10 | card == 1) {
            count--;
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
}
