import java.util.ArrayList;
import java.util.List;

public class FlowProblemSolver {

	private static final String WHITESPACE = " ";
	private static final String NEWLINE = "\n";
	
	private static Kattio io;
	private static int sizeOfV;
	private static int sourceVertex;
	private static int sinkVertex;

	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		
		Timer t = new Timer();
		t.start();

		//LÄSNING AV GRAF

		io = new Kattio(System.in,System.out);
		
		sizeOfV = io.getInt();
		sourceVertex = io.getInt()-1;
		sinkVertex = io.getInt()-1;
		int sizeOfE = io.getInt();
		
		ArrayList<Integer>[] neighbourList = new ArrayList[sizeOfV];

		int[][] capacity = new int[sizeOfV][sizeOfV];
		int[][] restCapacity = new int[sizeOfV][sizeOfV];
		
		for(int i = 0; i < sizeOfV; i++){
			neighbourList[i] = new ArrayList<Integer>();
		}
		
		for(int i = 0; i < sizeOfE; i++){
			int vertexFrom = io.getInt()-1; //Måste modifiera så vi får 0 indexerat
			int vertexTo = io.getInt()-1;
			int weight = io.getInt();
			
			if(!neighbourList[vertexFrom].contains(vertexTo))
				neighbourList[vertexFrom].add(vertexTo);

			if(!neighbourList[vertexTo].contains(vertexFrom))
				neighbourList[vertexTo].add(vertexFrom);
			
			capacity[vertexFrom][vertexTo] = weight;
			restCapacity[vertexFrom][vertexTo] = weight;
		}
		
		//Make matching
		int[][] flow = GraphAlgoritmLibrary.edmondKarp(neighbourList, sinkVertex, sourceVertex, restCapacity);
		
		//Print graph
		printPositiveFlow(neighbourList, flow);
		
		t.stop();
		io.println(t.getElapsedTime() + " ms");
		io.flush();
		io.close();
	}

	private static void printPositiveFlow(ArrayList<Integer>[] neighbourList, int[][] flow) {
		
		// Calculate total flow
		int totalFlow = 0;
		for(int i = 0; i < neighbourList[sinkVertex].size(); i++) {
			int neighbour = neighbourList[sinkVertex].get(i);
			totalFlow += flow[neighbour][sinkVertex];
		}
		
		int numberOfEdges = 0;
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0;i<neighbourList.length;i++) {
			
			List<Integer> neighbours = neighbourList[i];
			int nNeighbours = neighbours.size();
			for(int k = 0; k < nNeighbours; k++) {
				int neighbour = neighbours.get(k);
				int currentFlow = flow[i][neighbour];
				if(currentFlow > 0){
					numberOfEdges++;
					sb.append(i + 1);
					sb.append(WHITESPACE);
					sb.append(neighbour + 1);
					sb.append(WHITESPACE);
					sb.append(currentFlow);
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
	}

	/*
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
		

		int nNeighbours = edges.size();
		for(int i = 0;i<nNeighbours;i++){
			int neighbour = edges.get(i);
			int flow = edge.getFlow();
			if(flow<0){
				sum += flow;
			}
		}
				
		//Negativa är flödet in, posetiva är flödet ut
		return -sum;
	}*/
}
