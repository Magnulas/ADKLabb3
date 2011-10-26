
public class FlowEdge {

	int vertexOne;
	int vertexTwo;
	int capacity;
	int flow;
	
	public FlowEdge(int vertexOne, int vertexTwo, int capacity, int flow){
		this.vertexOne = vertexOne;
		this.vertexTwo = vertexTwo;
		this.capacity = capacity;
		this.flow = flow;
	}
}
