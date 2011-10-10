import java.util.Iterator;
import java.util.LinkedList;


public class FlowProblemSolver {

	static Kattio io;
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		
		//LÄSNING AV GRAF
		io = new Kattio(System.in,System.out);
		int sizeOfV = io.getInt();
		int sourceVertex = io.getInt()-1;
		int sinkVertex = io.getInt()-1;
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
		
		//Make matching
		edmondKarp(edges,sinkVertex,sourceVertex);
		
		//Print graph
		int totalFlow = flowSum(edges[sinkVertex]);
		
		io.println(sizeOfV);
		io.println((sourceVertex+1) + " " + (sinkVertex+1) + " " + totalFlow);
		
		int numberOfEdges = 0;
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0;i<edges.length;i++){
			Iterator<DirectedEdge> iter = edges[i].iterator();
			
			while(iter.hasNext()){
				DirectedEdge edge = iter.next();
				if(edge.flow>0){
					numberOfEdges++;
					sb.append((i + 1) + " " + (edge.neighbour + 1) + " " + edge.flow + "\n");
				}
			}
		}
				
		io.println(numberOfEdges);
		io.println(sb);
		
		io.flush();
	}

	private static int flowSum(LinkedList<DirectedEdge> edges) {
		
		int sum = 0;
		
		Iterator<DirectedEdge> iter = edges.iterator();
		
		while(iter.hasNext()){
			DirectedEdge edge = iter.next();
			if(edge.flow<0){
				sum += edge.flow;
			}
		}
		//Negativa är flödet in, posetiva är flödet ut
		return -sum;
	}

	private static void edmondKarp(LinkedList<DirectedEdge>[] edges, int sinkVertex, int sourceVertex) {
		
		LinkedList<Integer> path = null;
				
		while((path = breadthFirst(edges,sourceVertex,sinkVertex))!=null){
			
			DirectedEdge[] edgePath = new DirectedEdge[path.size()-1]; 
			Iterator<Integer> iter = path.iterator();    
			int vertexFrom = iter.next();	
			
			for(int i = 0;iter.hasNext();i++){
				int vertexTo = iter.next();
				edgePath[i] = getEdge(edges[vertexFrom],vertexTo);
				vertexFrom = vertexTo;
			}

			int leastFlow = getLeastFlow(edgePath);
			
			for(int i = 0;i<edgePath.length;i++){
				DirectedEdge edge = edgePath[i];
				DirectedEdge neightbourEdge = edge.neighbourEdge;
				
//				edge.capacity = edge.capacity - leastFlow;
//				neightbourEdge.capacity = neightbourEdge.capacity + leastFlow;
				
				edge.flow = edge.flow + leastFlow;
				neightbourEdge.flow = neightbourEdge.flow - leastFlow;
			}
		}
	}

	private static int getLeastFlow(DirectedEdge[] edges) {
		int leastFlow = Integer.MAX_VALUE;
		
		for(int i = 0;i<edges.length;i++){
			int currentFlow = edges[i].capacity-edges[i].flow;
			if(currentFlow<leastFlow){
				leastFlow = currentFlow;
			}
		}
		
		return leastFlow;
	}

	private static DirectedEdge getEdge(LinkedList<DirectedEdge> neighbourList, int neighbour) {
		Iterator<DirectedEdge> iter = neighbourList.iterator(); 
		
		while(iter.hasNext()){
			DirectedEdge edge = iter.next();
			if(edge.neighbour == neighbour){
				return edge;
			}
		}
		return null;
	}

	private static LinkedList<Integer> breadthFirst(LinkedList<DirectedEdge>[] edges, int startVertex, int endVertex) {
		
		LinkedList<LinkedList<Integer>> queue = new LinkedList<LinkedList<Integer>>();
		boolean[] used = new boolean[edges.length];
		LinkedList<Integer> startPath = new LinkedList<Integer>();
		
		startPath.add(startVertex);
		used[startVertex] = true;
		
		queue.add(startPath);
		
		while(!queue.isEmpty()){
			
			LinkedList<Integer> currentPath = queue.removeFirst();
			int nextVertex = currentPath.getLast();
			LinkedList<DirectedEdge> neighbours = edges[nextVertex];
			
			Iterator<DirectedEdge> iter = neighbours.iterator(); 
			
			while(iter.hasNext()){
				DirectedEdge edge = iter.next();
				if(used[edge.neighbour]==false&&(edge.capacity-edge.flow)>0){
					used[edge.neighbour] = true;
					
					LinkedList<Integer> newPath = new LinkedList<Integer>(currentPath);
					newPath.addLast(edge.neighbour);
					
					if(endVertex==edge.neighbour){
						return newPath;
					}
					
					queue.addLast(newPath);
				}
			}
		}
		
		return null;
	}
}
