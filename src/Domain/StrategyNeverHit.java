package Domain;

public class StrategyNeverHit implements StrategyInterface {
   
    @Override
    public boolean wantCard (Player player){
        return false;
    }
    
    @Override
    public void eval(Player player){};
}
