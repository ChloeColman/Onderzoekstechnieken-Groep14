package Domain;

import java.util.List;

public class StrategyBasic implements StrategyInterface {

    static boolean[][] hards = {
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, false, false, false, true, true, true, true},
        {true, true, false, false, false, false, false, true, true, true, true},
        {true, true, false, false, false, false, false, true, true, true, true},
        {true, true, false, false, false, false, false, true, true, true, true},
        {true, true, false, false, false, false, false, true, true, true, true},
        {false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false}
    };

    static boolean[][] softs = {
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true, true, true},
        {true, true, false, false, false, false, false, false, false, true, true},
        {false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false}
    };

    @Override
    public boolean wantCard(Player player) {
        List<Integer> cards = player.getCards();
        int temp = 0;
        if (cards.size() == 2 && player.getCards().contains(1)) {
            for (int card : cards) {
                if (card != 1) {
                    temp = card;
                }
                return softs[temp][player.getFace()];
            }
        }
        if (player.getCardTotal() < 21) {
            return hards[player.getCardTotal()][player.getFace()];
        } else {
            return false;
        }
    }
    
    @Override
    public void eval(Player player){};
}
