import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;

@SuppressWarnings("unchecked")
public class MaxFlow {
    int v, s, t, e;
    ArrayList<int[]> edgeList;
    ArrayList<Integer>[] adjList;
    int[] from, to, cap, flow;

    public void readFlowGraph() {
        int k = 0;
        for (int[] edge : edgeList) {
            int a = edge[0];
            int b = edge[1];
            int c = edge[2];
            
            from[k] = a;
            to[k] = b;
            cap[k] = c;
            adjList[a].add(k);

            from[k+1] = b;
            to[k+1] = a;
            cap[k+1] = 0;
            adjList[b].add(k+1);
            k += 2;
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
                for (int k : adjList[u]) {
                    int w = to[k];
                    if (!visited[w] && cap[k] - flow[k] > 0) {
                        visited[w] = true;
                        parent[w] = k;
                        q.add(w);
                    }
                }
            }
            if (!visited[t]) {
                break;
            }
            int r = Integer.MAX_VALUE;
            for (int x = t; x != s; x = from[parent[x]]) {
                int k = parent[x];
                r = Math.min(r, cap[k] - flow[k]);
            }
            for (int x = t; x != s; x = from[parent[x]]) {
                int k = parent[x];
                flow[k] += r;
                flow[k^1] -= r;
            }
        }
        for (int k = 0; k < 2 * edgeList.size(); k += 2) {
            if (flow[k] > 0) {
                flowGraph.add(new int[] {from[k], to[k], flow[k]});
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
            int m = 2 * edgeList.size();
            from = new int[m];
            to = new int[m];
            cap = new int[m];
            flow = new int[m];
        }
}
