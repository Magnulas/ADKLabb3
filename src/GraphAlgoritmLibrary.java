import java.util.ArrayList;
import java.util.LinkedList;

public class GraphAlgoritmLibrary {
	
	public static void edmondKarp(ArrayList<DirectedEdge>[] edges, int sinkVertex, int sourceVertex) {
		
		DirectedEdge[] parents = new DirectedEdge[edges.length];

		LinkedList<DirectedEdge> endVertexParents = new LinkedList<DirectedEdge>();
		
		while(multipleBreadthFirst(edges,sourceVertex,sinkVertex, parents, endVertexParents)){
	
			while(!endVertexParents.isEmpty()){
				ArrayList<DirectedEdge> edgePath = new ArrayList<DirectedEdge>();
				DirectedEdge currentEdge = endVertexParents.removeFirst();
				edgePath.add(currentEdge);
								
				while(currentEdge.getVertexFrom() != sourceVertex) {
					currentEdge = parents[currentEdge.getVertexFrom()];
					edgePath.add(currentEdge);
				}			

				int leastFlow = getLeastFlow(edgePath);
				
				if(leastFlow>0){
					int size = edgePath.size();
					for(int i = 0;i<size;i++){
						DirectedEdge edge = edgePath.get(i);
						int vertexFrom = edge.getVertexFrom();
						int neighbourVertex = edge.getNeighbour();
							
						DirectedEdge neightbourEdge = edge.getNeighbourEdge();
							
						if(neightbourEdge==null){
							neightbourEdge = new DirectedEdge(neighbourVertex,vertexFrom,0,edge);
							edge.setNeighbourEdge(neightbourEdge);
							edges[neighbourVertex].add(neightbourEdge);
						}
							
						int newFlow = edge.getFlow() + leastFlow;
						edge.setFlow(newFlow);
						neightbourEdge.setFlow(-newFlow);
					}
				}
			}
		}
	}
	
	private static int getLeastFlow(ArrayList<DirectedEdge> edges) {
		int leastFlow = Integer.MAX_VALUE;
		
		for(int i = 0;i<edges.size();i++){
			DirectedEdge edge = edges.get(i);
			int currentFlow = edge.getResidualCapacity();
			if(currentFlow<leastFlow){
				leastFlow = currentFlow;
			}
			if(leastFlow==0){
				return leastFlow;
			}
		}
		
		return leastFlow;
	}
	
	private static boolean multipleBreadthFirst(ArrayList<DirectedEdge>[] edges, int startVertex, int endVertex, DirectedEdge[] parents, LinkedList<DirectedEdge> endVertexParents) {
		
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
					
					parents[neighbour] = edge;
					used[neighbour] = true;
					
					if(endVertex==neighbour){
						endVertexParents.addLast(edge);
						used[neighbour] = false;
		
						break;
					}
	
					queue.put(neighbour);
					
				}
			}
		}
		
		if(endVertexParents.isEmpty()){
			return false;
		} else{
			return true;
		}
	}
}
