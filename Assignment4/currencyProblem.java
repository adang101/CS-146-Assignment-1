import java.util.Arrays;
import java.util.List;

public class currencyProblem {
    public static int findMinCoins(List<Integer> denominations, Integer targetAmount) {
        int [] X = new int[targetAmount + 1];
        Arrays.fill(X, Integer.MAX_VALUE);
        X[0] = 0;
        for(int i = 1; i <= targetAmount; i++) {
            for(int coin : denominations) {
                if(i >= coin) {
                    X[i] = Math.min(X[i], X[i - coin] + 1);
                }
            }
        }
        return X[targetAmount];
    }
}  
