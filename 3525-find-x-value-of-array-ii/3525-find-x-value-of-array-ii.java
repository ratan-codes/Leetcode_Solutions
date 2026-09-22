class Solution {
    class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            prod = 1 % k;
            cnt = new int[k];
        }
    }

    int n, k;
    Node[] tree;

    Node merge(Node left, Node right) {
        Node res = new Node(k);

        res.prod = (left.prod * right.prod) % k;

        for (int i = 0; i < k; i++) {
            res.cnt[i] += left.cnt[i];
        }

        for (int i = 0; i < k; i++) {
            int rem = (i * left.prod) % k;
            res.cnt[rem] += right.cnt[i];
        }

        return res;
    }

    void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            int v = nums[l] % k;
            tree[node].prod = v;
            tree[node].cnt[v] = 1;
            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int l, int r, int idx, int value) {
        if (l == r) {
            tree[node] = new Node(k);
            value %= k;
            tree[node].prod = value;
            tree[node].cnt[value] = 1;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, value);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n];

        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Node(k);
        }

        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            update(1, 0, n - 1, index, value);

            Node res = query(1, 0, n - 1, start, n - 1);

            ans[i] = res.cnt[x];
        }

        return ans;
    }
}