import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphAlgoritmLibrary {
	
	public static void edmondKarp(LinkedList<DirectedEdge>[] edges, int sinkVertex, int sourceVertex) {
		
		int[] parents = new int[edges.length];
		
		while(breadthFirst(edges,sourceVertex,sinkVertex, parents)){
			int currentNode = sinkVertex;
			ArrayList<DirectedEdge> edgePath = new ArrayList<DirectedEdge>();
			while(currentNode != sourceVertex) {
				int parentNode = parents[currentNode];
				edgePath.add(getEdge(edges[parentNode], currentNode));
				currentNode = parentNode;
			}			

			int leastFlow = getLeastFlow(edgePath);
			for(DirectedEdge edge : edgePath) {

				DirectedEdge neightbourEdge = edge.getNeighbourEdge();
				
				int newFlow = edge.getFlow() + leastFlow;
				edge.setFlow(newFlow);
				neightbourEdge.setFlow(-newFlow);
			}
		}
	}
	
	private static int getLeastFlow(ArrayList<DirectedEdge> edges) {
		int leastFlow = Integer.MAX_VALUE;
		
		for(DirectedEdge edge : edges){
			int currentFlow = edge.getResidualCapacity();
			if(currentFlow<leastFlow){
				leastFlow = currentFlow;
			}
		}
		
		return leastFlow;
	}
	
	private static boolean breadthFirst(LinkedList<DirectedEdge>[] edges, int startVertex, int endVertex, int[] parents) {
		
		IntQueue queue = new IntQueue();
		boolean[] used = new boolean[edges.length];
		
		used[startVertex] = true;
		
		queue.put(startVertex);
		
		while(!queue.isEmpty()){
			
			int currentVertex = queue.get();
			LinkedList<DirectedEdge> neighbours = edges[currentVertex];
			
			Iterator<DirectedEdge> iter = neighbours.iterator(); 
			
			while(iter.hasNext()){
				DirectedEdge edge = iter.next();
				int neighbour = edge.getNeighbour();
				
				if(used[neighbour] == false && edge.getResidualCapacity()>0) {
					
					int nextVertex = neighbour;
					parents[nextVertex] = currentVertex;
					
					if(endVertex==neighbour){
						return true;
					}
					
					queue.put(nextVertex);
					used[nextVertex] = true;
				}
			}
		}
		
		return false;
	}

	private static DirectedEdge getEdge(LinkedList<DirectedEdge> neighbourList, int neighbour) {
		Iterator<DirectedEdge> iter = neighbourList.iterator(); 
		
		while(iter.hasNext()){
			DirectedEdge edge = iter.next();
			if(edge.getNeighbour() == neighbour){
				return edge;
			}
		}
		return null;
	}
}
