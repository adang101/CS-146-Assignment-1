import java.util.ArrayList;
import java.util.List;

public class longestIncreasingSubsequence {
    public static List<Integer> findLIS(List<Integer> X) {
        int n = X.size();
        int[] LIS = new int[n];

        for(int i = 0; i < n; i++) {
            LIS[i] = 1;
        }
        for(int i = n - 2; i >= 0; i--) {
            int max = 1;
            for(int j = i + 1; j < n; j++) {
                if(X.get(i) < X.get(j)) {
                    if(1 + LIS[j] > max) {
                        max = 1 + LIS[j];
                    }
                }
            }
            LIS[i] = max;
        }
        int maxLIS = 0;
        for(int i = 0; i < n; i++) {
            if(LIS[i] > maxLIS) {
                maxLIS = LIS[i];
            }
        }
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(LIS[i] == maxLIS) {
                result.add(X.get(i));
                maxLIS--;
            }
        }
        return result;
    }
}
