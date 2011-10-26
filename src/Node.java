
public class Node {
	
	Node parent;
	int id;
	
	public Node(int id) {
		this.id = id;
		this.parent = null;
	}
	
	public Node(int id, Node parent) {
		this.id = id;
		this.parent = parent;
	}

}