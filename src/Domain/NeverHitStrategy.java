package Domain;

public class NeverHitStrategy implements StrategyInterface {
   
    @Override
    public boolean wantCard (Player player){
        return false;
    }
}
