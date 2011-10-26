import java.util.Iterator;
import java.util.LinkedList;


public class GraphAlgoritmLibrary {

	public static void edmondKarp(FlowGraph graph){
		edmondKarp(graph.edges,graph.sinkVertex,graph.sourceVertex);
	}
	
	public static void edmondKarp(LinkedList<DirectedEdge>[] edges, int sinkVertex, int sourceVertex) {
		
		LinkedList<Integer> path = null;
				
		//Fixa så att retunerar edges inte vertexes
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
