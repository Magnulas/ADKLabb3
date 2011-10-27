import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

public class GraphAlgoritmLibrary {
	
	public static TreeSet<DirectedEdge> edmondKarp(ArrayList<DirectedEdge>[] edges, int sinkVertex, int sourceVertex) {
		
		int[] parents = new int[edges.length];
		
		TreeSet<DirectedEdge> edgesWithFlow = new TreeSet<DirectedEdge>();

		LinkedList<Integer> endVertexParents = new LinkedList<Integer>();
		
		while(multipleBreadthFirst(edges,sourceVertex,sinkVertex, parents, endVertexParents)){
	
			while(!endVertexParents.isEmpty()){
				ArrayList<DirectedEdge> edgePath = new ArrayList<DirectedEdge>();
				int currentNode = endVertexParents.removeFirst();
				edgePath.add(getEdge(edges[currentNode], sinkVertex));
								
				while(currentNode != sourceVertex) {
					int parentNode = parents[currentNode];
					DirectedEdge e = getEdge(edges[parentNode], currentNode);
					if(e==null){
						edgePath.clear();
						break;
					}
					edgePath.add(e);
					currentNode = parentNode;
				}			

				int leastFlow = getLeastFlow(edgePath);
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
						edgesWithFlow.add(neightbourEdge);
					}
					
					int newFlow = edge.getFlow() + leastFlow;
					edge.setFlow(newFlow);
					neightbourEdge.setFlow(-newFlow);
					
					if(newFlow==edge.getCapacity()){
						//Göra removes snabbare?
						edges[vertexFrom].remove(edge);
					}
					edgesWithFlow.add(edge);
				}
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
	
	private static boolean multipleBreadthFirst(ArrayList<DirectedEdge>[] edges, int startVertex, int endVertex, int[] parents, LinkedList<Integer> endVertexParents) {
		
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
					used[neighbour] = true;
					
					if(endVertex==neighbour){
						endVertexParents.addLast(currentVertex);
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
