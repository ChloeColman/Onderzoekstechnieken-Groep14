package Domain;

import java.security.SecureRandom;

public class Shoe {

    private int numberOfDecks;
    private int[] shoe;
    private int[] deck = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,};
    private static final SecureRandom randomNumbers = new SecureRandom();
    private int card = 0;
    private int count = 0;

    public Shoe(int numberOfDecks) {
        setNumberOfDecks(numberOfDecks);
        createShoe();
    }

    private void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    public int getTrueCount() {
        return count / numberOfDecks;
    }

    private void updateCount(int card) {
        if (card > 1 && card < 7) {
            count++;
        } else if (card == 10 | card == 1) {
            count--;
        }
    }

    private void createShoe() {
        shoe = new int[numberOfDecks * 52];
        int location = 0;
        for (int i = 1; i <= numberOfDecks; i++) {
            for (int j = 0; j <= 51; j++) {
                shoe[location] = deck[j];
                location++;
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < shoe.length; i++) {
            int sh = randomNumbers.nextInt(shoe.length);
            int temp = shoe[i];
            shoe[i] = shoe[sh];
            shoe[sh] = temp;
        }
    }

    public int deal() {
        int deal;
        if (card < shoe.length) {
            deal = shoe[card];
            card++;
            updateCount(deal);
            return deal;
        } else {
            shuffle();
            card = 0;
            deal = shoe[card];
            card++;
            return deal;
        }
    }

    @Override
    public String toString() {
        String output = "";
        int[] frequencies = new int[10];
        for (int i = 0; i <= (numberOfDecks* 52) - 1; i++) {
            output += shoe[i];
            output += " ";
            switch (shoe[i]) {
                case 1:
                    frequencies[0]++;
                    break;
                case 2:
                    frequencies[1]++;
                    break;
                case 3:
                    frequencies[2]++;
                    break;
                case 4:
                    frequencies[3]++;
                    break;
                case 5:
                    frequencies[4]++;
                    break;
                case 6:
                    frequencies[5]++;
                    break;
                case 7:
                    frequencies[6]++;
                    break;
                case 8:
                    frequencies[7]++;
                    break;
                case 9:
                    frequencies[8]++;
                    break;
                case 10:
                    frequencies[9]++;
                    break;
                default:
            }
        }
        for (int i = 1; i <= frequencies.length; i++) {
            output += System.lineSeparator() + "#" + i + "= " + frequencies[i - 1];
        }
        return output;
    }

}
