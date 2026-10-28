import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;

public class MaxFlow {
    int v, s, t, e;
    int cnt = 0;
    int[] head, next, to, cap, origCap;
    ArrayList<int[]> edgeList;
    public void readFlowGraph() {
        for (int[] edge : edgeList) {
            int a = edge[0];
            int b = edge[1];
            int c = edge[2];
            to[cnt] = b;
            cap[cnt] = c;
            origCap[cnt] = c;
            next[cnt] = head[a];
            head[a] = cnt++;
            to[cnt] = a;
            cap[cnt] = 0;
            origCap[cnt] = 0;
            next[cnt] = head[b];
            head[b] = cnt++;
        }
    }

    public List<int[]> edmondKarp() {
        List<int[]> flowGraph = new ArrayList<>();
        int[] parent = new int[v + 1];
        boolean[] visited = new boolean[v + 1];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        
        while (true) { 
            for (int i = 0; i <= v; i++) {
                visited[i] = false;
            }
            q.clear();
            q.add(s);
            visited[s] = true;
            while (!q.isEmpty() && !visited[t]) {
                int u = q.poll();
                for (int i = head[u]; i != -1; i = next[i]) {
                    int w = to[i];
                    if (cap[i] > 0 && !visited[w]) {
                        visited[w] = true;
                        parent[w] = i;
                        q.add(w);
                    }
                }
            }
            if (!visited[t]) {
                break;
            }
            int r = Integer.MAX_VALUE;
            for (int u = t; u != s; u = to[parent[u] ^ 1]) {
                r = Math.min(r, cap[parent[u]]);
            }
            for (int u = t; u != s; u = to[parent[u] ^ 1]) {
                cap[parent[u]] -= r;
                cap[parent[u] ^ 1] += r;
            }
        }
        for (int i = 0; i < cnt; i += 2) {
            int f = origCap[i] - cap[i];
            if (f > 0) {
                flowGraph.add(new int[] {to[i ^ 1], to[i], f});
            }
        }
        return flowGraph;
    }

        public MaxFlow(int v, int s, int t, int e, ArrayList<int[]> edgeList) {
            this.v = v;
            this.s = s;
            this.t = t;
            this.e = e;
            this.edgeList = edgeList;
            head = new int[v + 1];
            java.util.Arrays.fill(head, -1);
            int m = 2 * e;
            next = new int[m];
            to  = new int[m];
            cap = new int[m];
            origCap = new int[m];
        }
}
