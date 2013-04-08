package fmi.graph;

import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.standard.Edge;
import fmi.graph.standard.Node;
import fmi.graph.standard.Writer;

import java.io.File;
import java.io.IOException;

public class ExampleWriter {

	
	public static void main(String[] args) {
		
		Writer w = new Writer();
		
		try {
			//w.createBin(new File("test.bin"));
            w.create(new File("test.txt"));
			w.setNodeCount(1000);
			w.setEdgeCount(2000);

            /*MetaData data = new MetaData();
            data.AddComment("This is a totally awesome comment");
            data.AddComment("and here comes another comment that tells you how great this format is!");
            data.AddComment("We can even handle embedded : isn't that unbelievable");
            data.Add("Timestamp", "1337");
            data.Add("Origin", "CHConstructor");
            w.writeMetaData(data);*/
			
			for(int n=0;n<1000;n++)
			{
				w.writeNode(new Node(n, n, Math.random(), Math.random(), (int)(Math.random()*100), "Teststring 1"));
			}
			
			for(int m=0;m<2000;m++)
			{
				w.writeEdge(new Edge(m,m+1,(int)(Math.random()*100),(int)(Math.random()*100),"Teststring 2"));
			}
			w.close();
			
			
			
		} catch (IOException e){
			e.printStackTrace();
		} catch (InvalidFunctionException e){
            e.printStackTrace();
        }

	}

}
