import java.util.ArrayList;
import java.util.ArrayDeque;

public class MaxFlow {
    Kattio io;
    int v = io.getInt();
    int s = io.getInt();
    int t = io.getInt();
    int e = io.getInt();
    ArrayList<Integer>[] adjList = new ArrayList[v + 1];
    int[][] edges = new int[e][2];
    int[][] f = new int[v + 1][v + 1];
    int[][] c = new int[v + 1][v + 1];
    int[][] cf = new int[v + 1][v + 1];
    void readFlowGraph() {
        for (int i = 0; i <= v; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < e; i++) {
            int a = io.getInt();
            int b = io.getInt();
            int cap = io.getInt();
            if (c[a][b] == 0 && c[b][a] == 0) {
                adjList[a].add(b);
                adjList[b].add(a);
            }
            c[a][b] += cap;
            cf[a][b] += cap;
        }
    }

    void edmondKarp() {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int[] parent = new int[v + 1];
        q.add(s);
        boolean[] visited = new boolean[v + 1];
        while (true) {
            for (int i = 0; i <+ v; i++) {
                visited[i] = false;
            }
            visited[s] = true;
            while (!q.isEmpty() && !visited[t]) {
                int u = q.poll();
                for (int v : adjList[u]) {
                    if (cf[u][v] > 0 && !visited[v]){}
                        visited[v] = true;
                        parent[v] = u;
                        q.add(v);
                    }
                }
            }
        }

    }
    public static void main(String args[]) {
        new MaxFlow();
    }
}
