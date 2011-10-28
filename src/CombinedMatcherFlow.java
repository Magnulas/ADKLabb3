import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CombinedMatcherFlow {

	static Kattio io;
	private static int sizeOfX;
	private static int sizeOfY;
	private static int startVertex;
	private static int endVertex;
	
	public static void main(String args[]) throws IOException{
		
		//LÄSNING AV GRAF
		io = new Kattio(System.in,System.out);
		ArrayList<DirectedEdge>[] edges = reduceToFlowProblem();
		GraphAlgoritmLibrary.edmondKarp(edges,endVertex,startVertex);
		reduceToBipartitMatching(edges);
		
		io.close();
	}
	
	@SuppressWarnings("unchecked")
	private static ArrayList<DirectedEdge>[] reduceToFlowProblem() {
		//LÄS BIPARTIT GRAF
		sizeOfX = io.getInt();
		sizeOfY = io.getInt();
		int sizeOfE = io.getInt();
		
		int sizeOfV = sizeOfX + sizeOfY;
		
		ArrayList<DirectedEdge>[] edges = new ArrayList[sizeOfV+2];
		
		for(int i = 0;i<edges.length;i++){
			edges[i] = new ArrayList<DirectedEdge>();
		}
				
		startVertex = sizeOfV;
		endVertex = sizeOfV + 1;
		
		for(int i = 0;i<sizeOfE;i++){
			int vertexFrom = io.getInt()-1; //Måste modifiera så vi får 0 indexerat
			int vertexTo = io.getInt()-1;
			int weight = 1;
			DirectedEdge edge = new DirectedEdge(vertexFrom,vertexTo,weight);
			edges[vertexFrom].add(edge);
		}
		
		for(int i = 0;i<sizeOfX;i++){			
			int vertexTo = i;
			int weight = 1;
		
			DirectedEdge edge = new DirectedEdge(startVertex,vertexTo,weight);
			edges[startVertex].add(edge);
		}
		
		for(int i = 0;i<sizeOfY;i++){
			int vertexFrom = i+sizeOfX;
			int weight = 1;
		
			DirectedEdge edge = new DirectedEdge(vertexFrom,endVertex,weight);
			edges[vertexFrom].add(edge);
		}
		

		return edges;
	}
	
	private static void reduceToBipartitMatching(ArrayList<DirectedEdge>[] edges) {
		
		//LÄS FLÖDESGRAF
		StringBuilder builder = new StringBuilder();
		int sizeOfE = 0;
		
		for(int i = 0;i<edges.length-2;i++){
			
			List<DirectedEdge> neighbours = edges[i];
			int nNeighbours = neighbours.size();
			for(int k = 0;k<nNeighbours; k++) {
				DirectedEdge edge = neighbours.get(k);
				int neighbour = edge.getNeighbour();
				int flow = edge.getFlow();
				if(flow>0&&neighbour!=endVertex){
					builder.append((i + 1) + " " + (edge.getNeighbour() + 1) + "\n");
					sizeOfE++;
				}
			}
		}
		
		//SKRIV BIPARTITGRAF
		io.println(sizeOfX + " " + sizeOfY);		
		io.println(sizeOfE);
		io.println(builder);
		io.flush();
	}
}
