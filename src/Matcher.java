
public class Matcher {

	static Kattio io;
	
	public static void main(String args[]){
		
		//LÄSNING AV GRAF
		io = new Kattio(System.in,System.out);
		int sizeOfX = io.getInt();
		int sizeOfY = io.getInt();
		int sizeOfE = io.getInt();
		
		int sizeOfV = sizeOfX + sizeOfY;
		
		int[][] edges = new int[sizeOfE+sizeOfX+sizeOfY][2];
				
		int start = 0;
		int end = sizeOfV + 1;
		
		for(int i = 0;i<sizeOfE;i++){
			edges[i][0] = io.getInt();
			edges[i][1] = io.getInt();
		}
		
		for(int i = 0;i<sizeOfX;i++){
			edges[i+sizeOfE][0] = start;
			edges[i+sizeOfE][1] = i+1;
		}
		
		for(int i = 0;i<sizeOfY;i++){
			edges[i+sizeOfE+sizeOfX][0] = sizeOfX+1+i;
			edges[i+sizeOfE+sizeOfX][1] = end;
		}
		
		//UTMATNING TILL FLOW
		io.println(sizeOfV+2);
		io.println(start + " " + end);
		io.println(edges.length);
		
		for(int i = 0;i<edges.length;i++){
			io.println(edges[i][0] + " " + edges[i][1] + " 1");
		}
		io.flush();
		io.close();
		/*
		 * SKRIV UT:
		Den första raden består av ett heltal som anger antalet hörn i V.
		Den andra raden består av två heltal s och t som anger vilka hörn som är källa respektive utlopp.
		Den tredje raden består av ett tal som anger |E|, det vill säga antalet kanter i grafen.
		De följande |E| raderna består var och en av tre heltal som svarar mot en kant.
		*/
	}
}
