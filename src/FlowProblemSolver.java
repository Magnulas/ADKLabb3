import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class FlowProblemSolver {

	private static final String WHITESPACE = " ";
	private static final String NEWLINE = "\n";
	
	private static Kattio io;
	private static int sizeOfV;
	private static int sourceVertex;
	private static int sinkVertex;
	
	public static void main(String args[]){
		
		//LÄSNING AV GRAF
		io = new Kattio(System.in,System.out);
		LinkedList<DirectedEdge>[] edges = readFlowGraph();
		
		//Make matching
		GraphAlgoritmLibrary.edmondKarp(edges,sinkVertex,sourceVertex);
		
		//Print graph
		printPosetiveFlow(edges);
		io.close();
	}

	private static void printPosetiveFlow(LinkedList<DirectedEdge>[] edges) {
		int totalFlow = flowSum(edges[sinkVertex]);
		int numberOfEdges = 0;
		TreeSet<Integer> vertexes = new TreeSet<Integer>();
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0;i<edges.length;i++){
			Iterator<DirectedEdge> iter = edges[i].iterator();
			
			while(iter.hasNext()){
				DirectedEdge edge = iter.next();
//				if(edge.flow>0){
				int neighbour = edge.getNeighbour();
				int flow = edge.getFlow();
				if(flow>0){
					numberOfEdges++;
					sb.append(i + 1);
					sb.append(WHITESPACE);
//					sb.append(edge.neighbour + 1);
					sb.append(edge.getNeighbour() + 1);
					sb.append(WHITESPACE);
//					sb.append(edge.flow);
					sb.append(flow);
					sb.append(NEWLINE);
					
					if(!vertexes.contains(i)){
						vertexes.add(i);
					}
//					if(!vertexes.contains(edge.neighbour)){
//						vertexes.add(edge.neighbour);
					if(!vertexes.contains(neighbour)){
						vertexes.add(neighbour);
					}
				}
			}
		}

		//io.println(vertexes.size());
		io.println(sizeOfV);
		io.println((sourceVertex+1) + " " + (sinkVertex+1) + " " + totalFlow);
		io.println(numberOfEdges);
		io.println(sb);
		
		io.flush();
	}

	@SuppressWarnings("unchecked")
	private static LinkedList<DirectedEdge>[] readFlowGraph() {
		sizeOfV = io.getInt();
		sourceVertex = io.getInt()-1;
		sinkVertex = io.getInt()-1;
		int sizeOfE = io.getInt();
		
		LinkedList<DirectedEdge>[] edges = new LinkedList[sizeOfV];
		
		for(int i = 0;i<sizeOfV;i++){
			edges[i] = new LinkedList<DirectedEdge>();
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

	private static int flowSum(LinkedList<DirectedEdge> edges) {
		
		int sum = 0;
		
		Iterator<DirectedEdge> iter = edges.iterator();
		
		while(iter.hasNext()){
			DirectedEdge edge = iter.next();
//			if(edge.flow<0){
//				sum += edge.flow;
			int flow = edge.getFlow();
			if(flow<0){
				sum += flow;
			}
		}
		//Negativa är flödet in, posetiva är flödet ut
		return -sum;
	}
}
