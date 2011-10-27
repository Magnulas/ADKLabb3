import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;


public class GenerateBipartiteGraph {

	public static final String fileName = "testfall/HugeDenseBipartiteGraph";
	static int numberOfX = 100;
	static int numberOfY = 100;
	static int numberOfE = 10000;
	static Kattio io;
	static Random rand;
	
	public static void main(String args[]) throws IOException{
		
		File f = new File(fileName);
		f.createNewFile();
		
		OutputStream out = new FileOutputStream(f);
		io = new Kattio(System.in,out);
		
		io.println(numberOfX + " " + numberOfY);
		io.println(numberOfE);
		
		rand = new Random();
				
		for(int i = 1;i<=numberOfX;i++){
			
			for(int j = numberOfX+1;j<=numberOfX+numberOfY;j++){
				io.println(i + " " + j);
			}
			
//			printTwoEdges(i);
		}
		
		io.flush();
		io.close();
	}

	private static void printTwoEdges(int vertex) {
		// TODO Auto-generated method stub
		int firstVertex = rand.nextInt(numberOfY)+1+numberOfX;
		int secondVertex = firstVertex;
		
		while(secondVertex==firstVertex){
			secondVertex = rand.nextInt(numberOfY)+1+numberOfX;
		}
		
		io.println(vertex + " " + firstVertex);
		io.println(vertex + " " + secondVertex);
	}
}
