package Domain;

public class StrategyTreshold implements StrategyInterface {
    
    @Override
    public boolean wantCard(Player player){
        return player.getCardTotal() < player.getTreshold();
    }
    @Override
    public void eval(Player player){};
}
