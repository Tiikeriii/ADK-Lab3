import java.util.ArrayList;
import java.util.ArrayDeque;

@SuppressWarnings("unchecked")
public class MaxFlow {
    Kattio io = new Kattio(System.in, System.out);
    int v = io.getInt();
    int s = io.getInt();
    int t = io.getInt();
    int e = io.getInt();
    ArrayList<Integer>[] adjList = new ArrayList[v + 1];
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
        int totalFlow = 0;
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
            totalFlow += r;
        }
        int f = 0;
        for (int a = 1; a <= v; a++) {
            for (int b : adjList[a]) {
                if (c[a][b] > 0 &&  c[a][b] - cf[a][b] > 0) {
                    f++;
                }
            }
        }
        io.println(v);
        io.println(s + " " + t + " " + totalFlow);
        io.println(f);
        for (int a = 1; a <= v; a++) {
            for (int b : adjList[a]) {
                if (c[a][b] > 0 && c[a][b] - cf[a][b] > 0) {
                    io.println(a + " " + b + " " + (c[a][b] - cf[a][b]));
                }
            }
        }
        io.flush();
    }

        MaxFlow() {

            readFlowGraph();

            edmondKarp();

            io.close();
        }
    
    public static void main(String args[]) {
        new MaxFlow();
    }
}
