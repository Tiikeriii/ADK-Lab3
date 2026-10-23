/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
    Kattio io;
	int x, y, e;
	int[][] edges;
	int[][] matchedEdges;
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
    
    
    void writeFlowGraph() {
	int numEdges = x + y + e;
	int s = x + y + 1;
	int t = x + y + 2;
	int v = x + y + 2;
	// Skriv ut antal hörn och kanter samt källa och sänka
	io.println(v);
	io.println(s + " " + t);
	io.println(numEdges);
	for (int i = 1; i <= x; i++ ) {
		io.println(s + " " + i + " 1");
	}
	for (int i = 0; i < e; i++) {
		io.println(edges[i][0] + " " + edges[i][1] + " 1");
	}
	for (int i = x + 1; i <= x + y; i++) {
		io.println(i + " " + t + " 1");
	}
	// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
	io.flush();
	
	// Debugutskrift
	System.err.println("Skickade iväg flödesgrafen");
    }
    
    
    void readMaxFlowSolution() {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		int lines = io.getInt();
		
		matchedEdges = new int[totflow][2];
		maxMatch = 0;

		for (int i = 0; i < lines; ++i) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();
			if (a != s && b != t && f > 0) {
				matchedEdges[maxMatch][0] = a;
				matchedEdges[maxMatch][1] = b;
				maxMatch++;
			}
		}
    }
    
    
    void writeBipMatchSolution() {
		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(maxMatch);
		
		for (int i = 0; i < maxMatch; ++i) {
			int a = matchedEdges[i][0];
			int b = matchedEdges[i][1];
			// Kant mellan a och b ingår i vår matchningslösning
			io.println(a + " " + b);
		}
    }
    
    BipRed() {
	io = new Kattio(System.in, System.out);
	
	readBipartiteGraph();
	
	writeFlowGraph();
	
	readMaxFlowSolution();
	
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

