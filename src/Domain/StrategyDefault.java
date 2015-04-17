package Domain;

public class StrategyDefault implements StrategyInterface {

    @Override
    public boolean wantCard(Player player) {
        return player.getCardTotal() < 17;
    }
    
    @Override
    public void eval(Player player){};
}
