/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

import java.util.ArrayList;
import java.util.List;

public class BipRed {
    Kattio io;
	int x, y, e, s, t;
	int[][] edges;
	List<int[]> matchedEdges;
	int maxMatch;
    
    void readBipartiteGraph() {
		// Läs antal hörn och kanter
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();
		edges = new int[e][2];
	
		// Läs in kanterna
		for (int i = 0; i < e; ++i) {
			edges[i][0] = io.getInt();
			edges[i][1] = io.getInt();
		}
    }
    
    private List<int[]> writeFlowGraph() {
		int numEdges = x + y + e;
		s = x + y + 1;
		t = x + y + 2;
		int v = x + y + 2;
		ArrayList<int[]> edgeList = new ArrayList<>();
		for (int i = 1; i <= x; i++ ) {
			edgeList.add(new int[] {s, i, 1});
		}
		for (int i = 0; i < e; i++) {
			edgeList.add(new int[] {edges[i][0], edges[i][1], 1});
		}
		for (int i = x + 1; i <= x + y; i++) {
			edgeList.add(new int[] {i, t, 1});
		}
		MaxFlow flow = new MaxFlow(v, s, t, numEdges, edgeList);
		flow.readFlowGraph();
		return flow.edmondKarp();
    }
    
    void readMaxFlowSolution(List<int[]> flowGraph) {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)
		
		matchedEdges = new ArrayList<>();
		maxMatch = 0;

		for (int[] flow : flowGraph) {
			// Flöde f från a till b
			int a = flow[0];
			int b = flow[1];
			int f = flow[2];
			if (a != s && b != t && f > 0) {
				matchedEdges.add(new int[] {a, b});
			}
		}
    }
    
    
    void writeBipMatchSolution() {
		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(matchedEdges.size());
		
		for (int[] E : matchedEdges) {
			int a = E[0];
			int b = E[1];
			// Kant mellan a och b ingår i vår matchningslösning
			io.println(a + " " + b);
		}
		io.flush();
    }
    
    BipRed() {
	io = new Kattio(System.in, System.out);
	
	readBipartiteGraph();
	
	List<int[]> flowGraph = writeFlowGraph();
	
	readMaxFlowSolution(flowGraph);
	
	writeBipMatchSolution();

	// debugutskrift
	System.err.println("Bipred avslutar\n");

	// Kom ihåg att stänga ner Kattio-klassen
	io.close();
    }
    
    public static void main(String args[]) {
	new BipRed();
    }
}