public class DirectedEdge {

	private int vertexFrom;
	private int vertexTo;

	public DirectedEdge(int vertexFrom, int vertexTo) {
		this.vertexFrom = vertexFrom;
		this.vertexTo = vertexTo;
	}

	public int getVertexFrom() {
		return vertexFrom;
	}

	public int getVertexTo() {
		return vertexTo;
	}
	
	public boolean equals(Object o) {
		if(o instanceof DirectedEdge) {
			DirectedEdge otherEdge = (DirectedEdge)o;
			return otherEdge.vertexFrom == vertexFrom && otherEdge.vertexTo == vertexTo;
		}
		return false;
	}
}
