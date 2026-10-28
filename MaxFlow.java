import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;

@SuppressWarnings("unchecked")
public class MaxFlow {
    int v, s, t, e;
    ArrayList<int[]> edgeList;
    ArrayList<Integer>[] adjList;
    int[][] c;
    int[][] cf;
    public void readFlowGraph() {
        for (int[] edge : edgeList) {
            int a = edge[0];
            int b = edge[1];
            int cap = edge[2];
            if (c[a][b] == 0 && c[b][a] == 0) {
                adjList[a].add(b);
                adjList[b].add(a);
            }
            c[a][b] += cap;
            cf[a][b] += cap;
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
                for (int w : adjList[u]) {
                    if (cf[u][w] > 0 && !visited[w]) {
                        visited[w] = true;
                        parent[w] = u;
                        q.add(w);
                    }
                }
            }
            if (!visited[t]) {
                break;
            }
            int r = Integer.MAX_VALUE;
            for (int x = t; x != s; x = parent[x]) {
                r = Math.min(r, cf[parent[x]][x]);
            }
            for (int x = t; x != s; x = parent[x]) {
                cf[parent[x]][x] -= r;
                cf[x][parent[x]] += r;
            }
        }
        for (int a = 1; a <= v; a++) {
            for (int b : adjList[a]) {
                if (c[a][b] > 0 && c[a][b] - cf[a][b] > 0) {
                    flowGraph.add(new int[] {a, b, (c[a][b] - cf[a][b])});
                }
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
            adjList = new ArrayList[v + 1];
            for (int i = 0; i <= v; i++) {
                adjList[i] = new ArrayList<>();
            }
            c = new int[v + 1][v + 1];
            cf = new int[v + 1][v + 1];
        }
}
