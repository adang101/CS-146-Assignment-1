import java.util.List;

public class maximumRewards {
    public static int findMaxRewards(List<Integer> rewards) {
        int X = rewards.size();
        int [][] Y = new int[X][X];
        for(int i = 0; i < X; i++) {
            for(int j = 0; j < X; j++) {
                Y[i][j] = 0;
            }
        }
        for(int i = 1; i <= X; i++) {
            for(int j = 0; j <= X - i; j++) {
                int k = j + i - 1;
                int left = (j + 2 <= k) ? Y[j + 2][k] : 0;
                int right = (j + 1 <= k - 1) ? Y[j + 1][k - 1] : 0;
                int pickedLeft = rewards.get(j) + Math.min(left, right);

                left = (j <= k - 2) ? Y[j][k - 2] : 0;
                right = (j + 1 <= k - 1) ? Y[j + 1][k - 1] : 0;
                int pickedRight = rewards.get(k) + Math.min(left, right);

                Y[j][k] = Math.max(pickedLeft, pickedRight);
            }
        }
        return Y[0][X - 1];
    }
}
