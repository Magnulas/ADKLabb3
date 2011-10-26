import java.io.IOException;


public class Matcher {

	static Kattio io;
	private static int sizeOfX;
	private static int sizeOfY;
	private static int startVertex;
	private static int endVertex;
	
	public static void main(String args[]) throws IOException{
		
		//LÄSNING AV GRAF
		io = new Kattio(System.in,System.out);
		reduceToFlowProblem();
		/**
		 * Nu har vi skrivit till den svartalådan som löser flödesproblemet och nu ska
		 * vi läsa datan från flödesproblemet och skriva ut den bipartita matchningen
		 */
		reduceToBipartitMatching();
		
		io.close();
	}

	private static void reduceToBipartitMatching() {
	
		//LÄS FLÖDESGRAF
		io.getInt(); //sizeOfV, we don't need it
		io.getInt(); //startVertex, we don't need it
		io.getInt(); //endVertex, we don't need it
		io.getInt(); //total flow from start to end, we don't need it
		int sizeOfFlowE = io.getInt(); //size of E, we don't need it
		
		StringBuilder builder = new StringBuilder();
		
		int[][] edges = new int[sizeOfFlowE][2];
		
		for(int i = 0;i<sizeOfFlowE;i++){
			edges[i][0] = io.getInt(); //From
			edges[i][1] = io.getInt(); //To
			io.getInt(); //Flow, we don't need it
		}
		
		//SKRIV BIPARTITGRAF
		io.println(sizeOfX + " " + sizeOfY);

		int sizeOfE = 0;
		for(int i = 0;i<edges.length;i++){
			if(edges[i][0] != startVertex && edges[i][1] != endVertex){
				builder.append(edges[i][0] + " " + edges[i][1] + "\n");
				sizeOfE++;
			}
		}
		
		io.println(sizeOfE);
		io.println(builder);
		io.flush();
	}

	private static void reduceToFlowProblem() {
		//LÄS BIPARTIT GRAF
		sizeOfX = io.getInt();
		sizeOfY = io.getInt();
		int sizeOfE = io.getInt();
		
		int sizeOfV = sizeOfX + sizeOfY;
		
		int[][] edges = new int[sizeOfE+sizeOfX+sizeOfY][2];
				
		startVertex = sizeOfV + 1;
		endVertex = sizeOfV + 2;
		
		for(int i = 0;i<sizeOfE;i++){
			edges[i][0] = io.getInt();
			edges[i][1] = io.getInt();
		}
		
		for(int i = 0;i<sizeOfX;i++){
			edges[i+sizeOfE][0] = startVertex;
			edges[i+sizeOfE][1] = i+1;
		}
		
		for(int i = 0;i<sizeOfY;i++){
			edges[i+sizeOfE+sizeOfX][0] = sizeOfX+1+i;
			edges[i+sizeOfE+sizeOfX][1] = endVertex;
		}
		
		//UTMATNING TILL FLOW
		io.println(sizeOfV+2);
		io.println(startVertex + " " + endVertex);
		io.println(edges.length);
		
		for(int i = 0;i<edges.length;i++){
			io.println(edges[i][0] + " " + edges[i][1] + " 1");
		}
		io.flush();
	}
}
