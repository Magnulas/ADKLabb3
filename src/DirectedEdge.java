
public class DirectedEdge {
	
	private int vertexFrom;
	private int neighbour;
	private int capacity;
	private int flow;
	private DirectedEdge neighbourEdge;
	
	public DirectedEdge(int vertexFrom, int neighbour, int capacity) {
		this(vertexFrom, neighbour, capacity,null);
	}
	
	public DirectedEdge(int vertexFrom, int neighbour, int capacity, DirectedEdge neighbourEdge) {
		this.vertexFrom = vertexFrom;
		this.neighbour = neighbour;
		this.capacity = capacity;
		this.neighbourEdge = neighbourEdge;
	}
	
	public void setNeighbourEdge(DirectedEdge edge){
		neighbourEdge = edge;
	}
	
	public boolean setFlow(int newFlow){
		if(newFlow<=capacity){
			flow = newFlow;
			return true;
		}
		
		return false;
	}
	
	public int getVertexFrom(){
		return vertexFrom;
	}
	
	public int getNeighbour(){
		return neighbour;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public int getFlow(){
		return flow;
	}
	
	public int getResidualCapacity(){
		return capacity-flow;
	}
	
	public DirectedEdge getNeighbourEdge(){
		return neighbourEdge;
	}
}
