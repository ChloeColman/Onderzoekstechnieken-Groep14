package Domain;

import java.security.SecureRandom;
import java.util.TreeMap;

public class LearningStrategy implements StrategyInterface {

    public TreeMap<Integer, TreeMap<Boolean, Double>> map;
    private static final SecureRandom randomNumber = new SecureRandom();
    TreeMap temp;
    double trueAlpha;
    double falseAlpha;
    boolean chosen;

    public LearningStrategy() {
        this.map = new TreeMap<>();
    }

    @Override
    public boolean wantCard(Player player) {
        if (!map.containsKey(player.getCardTotal())) {
            map.put(player.getCardTotal(), new TreeMap<Boolean, Double>());
            TreeMap node = map.get(player.getCardTotal());
            node.put(true, (double)50);
            node.put(false, (double) 50);
        }
        return pick(player);
    }

    @Override
    public void eval(Player player) {
        if (player.getCardTotal() <= 21) {
            if (chosen) {
                trueAlpha = trueAlpha + Math.log((falseAlpha+100)/100);
                falseAlpha = 100 - trueAlpha;
            } else {
                falseAlpha = falseAlpha + Math.log((trueAlpha+100)/100);
                trueAlpha = 100 - falseAlpha;
            }
        } else {
            if (chosen) {
                falseAlpha = falseAlpha + Math.log((trueAlpha+100)/100);
                trueAlpha = 100 - falseAlpha;
            } else {
                trueAlpha = trueAlpha + Math.log((falseAlpha+100)/100);
                falseAlpha = 100 - trueAlpha;
            }
        }
        temp.put(true, trueAlpha);
        temp.put(false, falseAlpha);
    }

    public boolean pick(Player player) {
        temp = map.get(player.getCardTotal());
        trueAlpha = (double) temp.get(true);
        falseAlpha = (double) temp.get(false);
        chosen = randomNumber.nextDouble()*100 >= falseAlpha;
        return chosen;
    }
}
