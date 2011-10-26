import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class GraphAlgoritmLibrary {

	public static void edmondKarp(FlowGraph graph){
		edmondKarp(graph.edges,graph.sinkVertex,graph.sourceVertex);
	}
	
	public static void edmondKarp(LinkedList<DirectedEdge>[] edges, int sinkVertex, int sourceVertex) {
		
		int[] parents = new int[edges.length];
		

		//Fixa så att retunerar edges inte vertexes
		while(breadthFirst(edges,sourceVertex,sinkVertex, parents)){
			int currentNode = sinkVertex;
			ArrayList<DirectedEdge> edgePath = new ArrayList<DirectedEdge>();
			while(currentNode != sourceVertex) {
				int parentNode = parents[currentNode];
				edgePath.add(getEdge(edges[parentNode], currentNode));
				currentNode = parentNode;
			}
			//DirectedEdge[] edgePath = new DirectedEdge[path.size()-1]; 
			//Iterator<Integer> iter = path.iterator();    
//			int vertexFrom = iter.next();	
			

			int leastFlow = getLeastFlow(edgePath);
			for(DirectedEdge edge : edgePath) {

				DirectedEdge neightbourEdge = edge.neighbourEdge;
				
//				edge.capacity = edge.capacity - leastFlow;
//				neightbourEdge.capacity = neightbourEdge.capacity + leastFlow;
				
				edge.flow = edge.flow + leastFlow;
				neightbourEdge.flow = neightbourEdge.flow - leastFlow;
			}
			
			/*
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
			}*/
		}
	}
	
	private static int getLeastFlow(ArrayList<DirectedEdge> edges) {
		int leastFlow = Integer.MAX_VALUE;
		
		for(DirectedEdge edge : edges){
			int currentFlow = edge.capacity-edge.flow;
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
				if(used[edge.neighbour] == false && (edge.capacity-edge.flow)>0) {
					
					int nextVertex = edge.neighbour;
					parents[nextVertex] = currentVertex;
					
					if(endVertex==edge.neighbour){
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
			if(edge.neighbour == neighbour){
				return edge;
			}
		}
		return null;
	}
}
