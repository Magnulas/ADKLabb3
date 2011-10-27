import java.util.ArrayList;

public class GraphAlgoritmLibrary {
	
	public static int[][] edmondKarp(ArrayList<Integer>[] edges, int sinkVertex, int sourceVertex, int[][]restCapacity) {
		
		int sizeOfV = restCapacity.length;
		int[][] flow = new int[sizeOfV][sizeOfV];
		
		int[] parents = new int[edges.length];
		
		/**
		 * Låt sökningen ske på en restflödesgraf som består av samma kanter/objekt
		 * som edges(hela grafen) men ha bara med de kanter som har restflöde?
		 */
		while(breadthFirst(edges, sourceVertex, sinkVertex, parents, restCapacity)){
			
			int currentNode = sinkVertex;
			int leastFlow = Integer.MAX_VALUE;
			while(currentNode != sourceVertex) {
				int parentNode = parents[currentNode];
				if(restCapacity[parentNode][currentNode] < leastFlow) {
					leastFlow = restCapacity[parentNode][currentNode];
				}
				currentNode = parentNode;
			}
			
			currentNode = sinkVertex;
			while(currentNode != sourceVertex) {
				/*
				 * f[u,v]:=f[u,v]+r; f[v,u]:= -f[u,v]
         		 * cf[u,v]:=c[u,v] - f[u,v]; cf[v,u]:=c[v,u] - f[v,u] 
				 */
				int parentNode = parents[currentNode];
				flow[parentNode][currentNode] += leastFlow;
				flow[currentNode][parentNode] = -flow[parentNode][currentNode];
				restCapacity[parentNode][currentNode] -= leastFlow;
				restCapacity[currentNode][parentNode] += leastFlow;
				
				currentNode = parentNode;
			}
		}
		return flow;
	}
	
	private static boolean breadthFirst(ArrayList<Integer>[] edges, int startVertex, int endVertex, int[] parents, int[][] restCapacity) {
		
		IntQueue queue = new IntQueue();
		boolean[] used = new boolean[edges.length];
		
		used[startVertex] = true;
		
		queue.put(startVertex);
		
		while(!queue.isEmpty()){
			
			int currentVertex = queue.get();
			ArrayList<Integer> neighbours = edges[currentVertex];
			
			int nNeighbours = neighbours.size();
			for(int i = 0; i < nNeighbours; i++){
				int neighbour = neighbours.get(i);
				
				if(!used[neighbour] && restCapacity[currentVertex][neighbour] > 0) {
					
					int nextVertex = neighbour;
					parents[nextVertex] = currentVertex;
					
					if(neighbour == endVertex){
						return true;
					}
					
					queue.put(nextVertex);
					used[nextVertex] = true;
				}
			}
		}
		
		return false;
	}
}
