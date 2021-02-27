package ox46;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Random;

public class OrdinaryBalanceTree {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        init();
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int t = in.nextInt();
            int key = in.nextInt();
            if (t == 1) {
                insert(root, key, 0);
            } else if (t == 2) {
                remove(root, key, 0);
            } else if (t == 3) {
                out.println(getRankByKey(root, key) - 1);
            } else if (t == 4) {
                out.println(getKeyByRank(root, key + 1));
            } else if (t == 5) {
                out.println(getPrev(root, key));
            } else {
                out.println(getNext(root, key));
            }
        }
    }

    int N = 100010;
    int inf = 0x3f3f3f3f;
    Node[] tr = new Node[N];
    int idx;
    int root;

    void init() {
        idx = 0;
        tr[0] = new Node();
        createNode(-inf);
        createNode(inf);
        // 根节点1号点, 右儿子2号点
        root = 1;
        tr[1].r = 2;
        tr[1].parent = 0;
        tr[2].parent = 1;
        pushUp(root);
        if (tr[1].val < tr[2].val) {
            zag(root);
        }
    }

    int createNode(int key) {
        tr[++idx] = new Node();
        tr[idx].key = key;
        tr[idx].val = new Random().nextInt(inf);
        tr[idx].cnt = 1;
        tr[idx].size = 1;
        return idx;
    }

    void insert(int p, int key, int pre) {
        if (p == 0) {
            int cur = createNode(key);
            tr[cur].parent = pre;
            if (tr[pre].key > key) {
                tr[pre].l = cur;
            } else {
                tr[pre].r = cur;
            }
            return;
        }
        if (tr[p].key == key) {
            tr[p].cnt++;
            pushUp(p);
            return;
        }
        if (tr[p].key > key) {
            insert(tr[p].l, key, p);
            if (tr[tr[p].l].val > tr[p].val) {
                zig(p);
            }
        } else {
            insert(tr[p].r, key, p);
            if (tr[tr[p].r].val > tr[p].val) {
                zag(p);
            }
        }
        pushUp(p);
    }

    void remove(int p, int key, int pre) {
        if (p == 0) {
            return;
        }
        if (tr[p].key == key) {
            if (tr[p].cnt > 1) {
                tr[p].cnt--;
                pushUp(p);
                return;
            }
            // 如果不是叶子节点
            if (tr[p].l != 0 || tr[p].r != 0) {
                if (tr[p].r == 0 || tr[tr[p].l].val > tr[tr[p].r].val) {
                    zig(p);
                } else {
                    zag(p);
                }
                remove(p, key, tr[p].parent);
            } else {
                // 叶子节点 直接删除
                if (tr[pre].l == p) {
                    tr[pre].l = 0;
                } else {
                    tr[pre].r = 0;
                }
            }
            pushUp(pre);
            return;
        }
        if (tr[p].key > key) {
            remove(tr[p].l, key, p);
        } else {
            remove(tr[p].r, key, p);
        }
        pushUp(p);
    }

    int getRankByKey(int p, int key) {
        if (p == 0) {
            return 0;
        }
        if (tr[p].key == key) {
            return tr[tr[p].l].size + 1;
        }
        if (tr[p].key > key) {
            return getRankByKey(tr[p].l, key);
        }
        return tr[tr[p].l].size + tr[p].cnt + getRankByKey(tr[p].r, key);
    }

    int getKeyByRank(int p, int rank) {
        if (p == 0) {
            return inf;
        }
        if (tr[tr[p].l].size >= rank) {
            return getKeyByRank(tr[p].l, rank);
        }
        if (tr[tr[p].l].size + tr[p].cnt >= rank) {
            return tr[p].key;
        }
        return getKeyByRank(tr[p].r, rank - tr[tr[p].l].size - tr[p].cnt);
    }

    int getPrev(int p, int key) {
        if (p == 0) {
            return -inf;
        }
        if (tr[p].key >= key) {
            return getPrev(tr[p].l, key);
        }
        return Math.max(tr[p].key, getPrev(tr[p].r, key));
    }

    int getNext(int p, int key) {
        if (p == 0) {
            return inf;
        }
        if (tr[p].key <= key) {
            return getNext(tr[p].r, key);
        }
        return Math.min(tr[p].key, getNext(tr[p].l, key));
    }


    // 右旋
    private void zig(int p) {
        if (p == root) {
            root = tr[p].l;
        }
        int q = tr[p].l;
        tr[p].l = tr[q].r;
        tr[tr[q].r].parent = p;
        tr[q].parent = tr[p].parent;
        if (tr[tr[p].parent].l == p) {
            tr[tr[p].parent].l = q;
        } else {
            tr[tr[p].parent].r = q;
        }
        tr[p].parent = q;
        tr[q].r = p;
        pushUp(p);
        pushUp(q);

    }

    // 左旋
    private void zag(int p) {
        if (p == root) {
            root = tr[p].r;
        }
        int q = tr[p].r;
        tr[p].r = tr[q].l;
        tr[tr[q].l].parent = p;
        tr[q].parent = tr[p].parent;
        if (tr[tr[p].parent].l == p) {
            tr[tr[p].parent].l = q;
        } else {
            tr[tr[p].parent].r = q;
        }
        tr[p].parent = q;
        tr[q].l = p;
        pushUp(p);
        pushUp(q);
    }


    void pushUp(int p) {
        if (p == 0) {
            return;
        }
        tr[p].size = tr[tr[p].l].size + tr[tr[p].r].size + tr[p].cnt;
    }

    class Node {
        int l;
        int r;
        // 节点键值
        int key;
        // 堆中权值
        int val;
        // 这个数出现次数
        int cnt;
        // 每个(节点)子树个数
        int size;
        // 父节点
        int parent;

        public Node() {
            l = 0;
            r = 0;
            size = 0;
            cnt = 0;
        }

    }

}
