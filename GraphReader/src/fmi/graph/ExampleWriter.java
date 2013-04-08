package fmi.graph;

import java.io.File;
import java.io.IOException;

import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.standard.*;

public class ExampleWriter {

	
	public static void main(String[] args) {
		
		Writer w = new Writer();
		
		try {
			w.create(new File("test.txt"));
			w.setNodeCount(1000);
			w.setEdgeCount(2000);
			
			for(int n=0;n<1000;n++)
			{
				w.writeNode(new Node(n, n, Math.random(), Math.random(), (int)(Math.random()*100), "Teststring 1"));
			}
			
			for(int m=0;m<2000;m++)
			{
				w.writeEdge(new Edge(m,m+1,(int)(Math.random()*100),(int)(Math.random()*100),"Teststring 2"));
			}
			w.close();
			
			
			
		} catch (IOException | InvalidFunctionException e) {
			e.printStackTrace();
		}

	}

}
