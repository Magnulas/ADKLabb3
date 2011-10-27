import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlowProblemSolver {

	private static final String WHITESPACE = " ";
	private static final String NEWLINE = "\n";
	
	private static Kattio io;
	private static int sizeOfV;
	private static int sourceVertex;
	private static int sinkVertex;
	
	public static void main(String args[]){
		
		Timer t = new Timer();
		t.start();
		
		//LÄSNING AV GRAF
		io = new Kattio(System.in,System.out);
		ArrayList<DirectedEdge>[] edges = readFlowGraph();
		
		//Make matching
		GraphAlgoritmLibrary.edmondKarp(edges,sinkVertex,sourceVertex);
		
		//Print graph
		printPosetiveFlow(edges);
		
		t.stop();
		io.println(t.getElapsedTime() + " ms");
		io.flush();
		io.close();
	}

	private static void printPosetiveFlow(List<DirectedEdge>[] edges) {
		int totalFlow = flowSum(edges[sinkVertex]);
		int numberOfEdges = 0;
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0;i<edges.length;i++){
			Iterator<DirectedEdge> iter = edges[i].iterator();
			
			while(iter.hasNext()){
				DirectedEdge edge = iter.next();
				int flow = edge.getFlow();
				if(flow>0){
					numberOfEdges++;
					sb.append(i + 1);
					sb.append(WHITESPACE);
					sb.append(edge.getNeighbour() + 1);
					sb.append(WHITESPACE);
					sb.append(flow);
					sb.append(NEWLINE);
				}
			}
		}

		io.println(sizeOfV);
		
		io.print(sourceVertex+1);
		io.print(WHITESPACE); 
		io.print(sinkVertex+1); 
		io.print(WHITESPACE);
		io.println(totalFlow);
		
		io.println(numberOfEdges);
		io.println(sb);
		
		io.flush();
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<DirectedEdge>[] readFlowGraph() {
		sizeOfV = io.getInt();
		sourceVertex = io.getInt()-1;
		sinkVertex = io.getInt()-1;
		int sizeOfE = io.getInt();
		
		ArrayList<DirectedEdge>[] edges = new ArrayList[sizeOfV];
		
		for(int i = 0;i<sizeOfV;i++){
			edges[i] = new ArrayList<DirectedEdge>();
		}
		
		for(int i = 0;i<sizeOfE;i++){
			int vertexFrom = io.getInt()-1; //Måste modifiera så vi får 0 indexerat
			int vertexTo = io.getInt()-1;
			int weight = io.getInt();
		
			DirectedEdge firstEdge = new DirectedEdge(vertexFrom,vertexTo,weight);
			DirectedEdge secondEdge = new DirectedEdge(vertexTo,vertexFrom,0,firstEdge);
			firstEdge.setNeighbourEdge(secondEdge);
			edges[vertexFrom].add(firstEdge);
			edges[vertexTo].add(secondEdge);
		}
		return edges;
	}

	private static int flowSum(List<DirectedEdge> edges) {
		
		int sum = 0;
		
		
		for(int i = 0;i<edges.size();i++){
			DirectedEdge edge = edges.get(i);
			int flow = edge.getFlow();
			if(flow<0){
				sum += flow;
			}
		}
				
		//Negativa är flödet in, posetiva är flödet ut
		return -sum;
	}
}
