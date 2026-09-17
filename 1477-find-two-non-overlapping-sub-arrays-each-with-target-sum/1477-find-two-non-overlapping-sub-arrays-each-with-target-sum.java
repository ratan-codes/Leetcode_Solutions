class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] dp = new int[n];
        int INF = 1000000;

        for (int i = 0; i < n; i++)
            dp[i] = INF;

        int left = 0;
        int sum = 0;
        int ans = INF;
        int best = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left++];
            }

            if (sum == target) {
                int len = right - left + 1;

                if (left > 0 && dp[left - 1] != INF) {
                    ans = Math.min(ans, len + dp[left - 1]);
                }

                best = Math.min(best, len);
            }

            dp[right] = best;
        }

        return ans == INF ? -1 : ans;
    }
}