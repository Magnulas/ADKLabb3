import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;


public class GenerateFlowGraph {

	public static void main(String args[]) throws IOException{
		String fileToRead;
		String fileToWrite;
		
		fileToRead = GenerateBipartiteGraph.fileName;
		fileToWrite = "testfall/HugeDenseFlowGraph";	
		
		File fRead = new File(fileToRead);
		File fWrite = new File(fileToWrite);
		
		FileInputStream is = new FileInputStream(fRead);
		PrintStream out = new PrintStream(fWrite);
		
		System.setIn(is);
		System.setOut(out);
		System.out.println("We don't have a black box for matcher sop we get an exception");
		Matcher.main(null);
		
	}
}
