package fmi.graph;
import java.io.File;

import java.io.IOException;

import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;
import fmi.graph.standard.*;



public class ExampleReader {
	public static void main(String[] args) {
		Reader r = new Reader();
		@SuppressWarnings("unused")
		Node n;
		@SuppressWarnings("unused")
		Edge e;
		int nodes=0;
		int edges =0;
		long start;
		try {
			nodes=0;
			edges =0;
			System.out.println("Reading File");
			start = System.currentTimeMillis();
			//r.open(new File("test.txt"));
			r.openBin(new File("test.bin"));
			//r.openGZip(new File("test.gz"));
			while(r.hasNextNode())
			{
				n = r.nextNode();
				//System.out.println(n);
				nodes++;
			}
			System.out.println("Nodes gelesen: "+nodes+" Nodes vorhanden: "+r.getNodeCount());
			start = System.currentTimeMillis()-start;
			System.out.println(start+" Millisekunden Ladezeit");
			start = System.currentTimeMillis();
			while(r.hasNextEdge())
			{
				e = r.nextEdge();
				//System.out.println(e);
				edges++;
			}
			System.out.println("Edges gelesen: "+edges+" Edges vorhanden: "+r.getEdgeCount());
			start = System.currentTimeMillis()-start;
			System.out.println(start+" Millisekunden Ladezeit");
			r.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NoGraphOpenException ex) {
			ex.printStackTrace();
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
		}
		

	}

}
