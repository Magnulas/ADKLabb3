
public class DirectedEdge {
	
	int vertexFrom;
	int neighbour;
	int capacity;
	int flow;
	DirectedEdge neighbourEdge;
	
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
	
//	public int getCapacity(){
//		
//		return capacity;
//	}
//	
//	public boolean setFlow(int newFlow){
//		if(newFlow<=capacity){
//			flow = newFlow;
//			return true;
//		}
//		
//		return false;
//	}
//	
//	public int getFlow(){
//		
//		return capacity;
//	}
}
