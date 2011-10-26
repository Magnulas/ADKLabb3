
public class CombinedMatcherAndFlow {

	/**
	 * Only an outline of how the combined step could look
	 */
	
	static Kattio io;
		
	public static void main(String args[]){
		
		io = new Kattio(System.in,System.out);
		
		FlowGraph reducedGraph = readAndreduceToFlowGraph();
		GraphAlgoritmLibrary.edmondKarp(reducedGraph);
		reduceToBipartieGraphAndPrint(reducedGraph);
		
		io.close();
	}

	private static void reduceToBipartieGraphAndPrint(FlowGraph graph) {
		// TODO Auto-generated method stub
		
	}

	private static FlowGraph readAndreduceToFlowGraph() {
		// TODO Auto-generated method stub
		return null;
	}
}
