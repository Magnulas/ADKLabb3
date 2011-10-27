import java.util.ArrayList;
import java.util.TreeSet;

public class GraphAlgoritmLibrary {
	
	public static TreeSet<DirectedEdge> edmondKarp(ArrayList<DirectedEdge>[] edges, int sinkVertex, int sourceVertex) {
		
		int[] parents = new int[edges.length];
		
		TreeSet<DirectedEdge> edgesWithFlow = new TreeSet<DirectedEdge>();

		while(breadthFirst(edges,sourceVertex,sinkVertex, parents)){
			
			int currentNode = sinkVertex;
			ArrayList<DirectedEdge> edgePath = new ArrayList<DirectedEdge>();
			while(currentNode != sourceVertex) {
				int parentNode = parents[currentNode];
				edgePath.add(getEdge(edges[parentNode], currentNode));
				currentNode = parentNode;
			}			

			int leastFlow = getLeastFlow(edgePath);
			for(int i = 0;i<edgePath.size();i++){
				DirectedEdge edge = edgePath.get(i);
				int vertexFrom = edge.getVertexFrom();
				int neighbourVertex = edge.getNeighbour();
				
				DirectedEdge neightbourEdge = edge.getNeighbourEdge();
				
				if(neightbourEdge==null){
					neightbourEdge = new DirectedEdge(neighbourVertex,vertexFrom,0,edge);
					edge.setNeighbourEdge(neightbourEdge);
					edges[neighbourVertex].add(neightbourEdge);
					edgesWithFlow.add(neightbourEdge);
				}
				
				int newFlow = edge.getFlow() + leastFlow;
				edge.setFlow(newFlow);
				neightbourEdge.setFlow(-newFlow);
				
				if(newFlow==edge.getCapacity()){
					//Göra removes snabbare?
					edges[vertexFrom].remove(edge);
					neightbourEdge.setNeighbourEdge(null);
				}
				
				edgesWithFlow.add(edge);
			}
		}
			
		return edgesWithFlow;
	}
	
	private static int getLeastFlow(ArrayList<DirectedEdge> edges) {
		int leastFlow = Integer.MAX_VALUE;
		
		for(int i = 0;i<edges.size();i++){
			DirectedEdge edge = edges.get(i);
			int currentFlow = edge.getResidualCapacity();
			if(currentFlow<leastFlow){
				leastFlow = currentFlow;
			}
		}
		
		return leastFlow;
	}
	
	private static boolean breadthFirst(ArrayList<DirectedEdge>[] edges, int startVertex, int endVertex, int[] parents) {
		
		IntQueue queue = new IntQueue();
		boolean[] used = new boolean[edges.length];
		
		used[startVertex] = true;
		
		queue.put(startVertex);
		
		while(!queue.isEmpty()){
			
			int currentVertex = queue.get();
			ArrayList<DirectedEdge> neighbours = edges[currentVertex];
			
			int nNeighbours = neighbours.size();
			for(int i = 0;i<nNeighbours;i++){
				DirectedEdge edge = neighbours.get(i);
				int neighbour = edge.getNeighbour();
				
				if(used[neighbour] == false && edge.getResidualCapacity()>0) {
					
					parents[neighbour] = currentVertex;
					
					if(endVertex==neighbour){
						return true;
					}
					
					queue.put(neighbour);
					used[neighbour] = true;
				}
			}
		}
		
		return false;
	}

	private static DirectedEdge getEdge(ArrayList<DirectedEdge> neighbourList, int neighbour) {

		int nNeighbours = neighbourList.size();
		for(int i = 0; i<nNeighbours;i++){
			DirectedEdge edge = neighbourList.get(i);
			if(edge.getNeighbour() == neighbour){
				return edge;
			}
		}
		
		return null;
	}
}
