
public class DirectedEdge {//implements Comparable{
	
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
		flow = 0;
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

//	@Override
//	public int compareTo(Object o) {
//		
//		if(o instanceof DirectedEdge){
//			DirectedEdge e = (DirectedEdge) o;
//			
//			if(e.vertexFrom == vertexFrom && e.neighbour == neighbour){
//				return 0;
//			} 
//			
//			int eVertexFrom = e.getVertexFrom();
//			
//			if(vertexFrom >= eVertexFrom){
//				
//				if(vertexFrom==eVertexFrom){
//					if(neighbour > e.getNeighbour()){
//						return 1;
//					} else{
//						return -1;
//					}
//				}
//				
//				return 1;
//			} else{
//				return -1;
//			}
//		} else{
//			throw new ClassCastException();
//		}
//	}
}
