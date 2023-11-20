import java.util.ArrayList;
import java.util.List;

public class longestCommonSubsequence {
    public static List<Integer> findLCS(List<Integer> X, List<Integer> Y) {
        int m = X.size();
        int n = Y.size();

        // Initialize 2D array to store list lengths
        int[][] c = new int[m + 1][n + 1];

        // Build 2D matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.get(i - 1).equals(Y.get(j - 1))) {
                    c[i][j] = 1 + c[i - 1][j - 1];
                } else {
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
                }
            }
        }

        // Find LCS in 2D matrix
        List<Integer> result = new ArrayList<>();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (X.get(i - 1).equals(Y.get(j - 1))) {
                result.add(0, X.get(i - 1));
                i--;
                j--;
            } else if (c[i - 1][j] > c[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return result;
    }
}
