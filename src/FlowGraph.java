import java.util.LinkedList;


public class FlowGraph {

	int[] vertexes;
	LinkedList<DirectedEdge>[] edges;
	int sinkVertex;
	int sourceVertex;
	
	public FlowGraph(int[] vertexes,LinkedList<DirectedEdge>[] edges,int sourceVertex,int sinkVertex){
		this.vertexes = vertexes;
		this.edges = edges;
		this.sinkVertex = sinkVertex;
		this.sourceVertex = sourceVertex;
	}
}
