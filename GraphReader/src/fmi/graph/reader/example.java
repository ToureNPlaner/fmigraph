package fmi.graph.reader;
import java.io.File;

import java.io.IOException;

import fmi.graph.reader.exceptions.NoGraphOpenException;
import fmi.graph.reader.exceptions.NoSuchElementException;
import fmi.graph.reader.maxspeed.*;



public class example {
	public static void main(String[] args) {
		Reader r = new Reader();
		@SuppressWarnings("unused")
		Node n;
		@SuppressWarnings("unused")
		Edge e;
		int nodes=0;
		int edges =0;
		long start = System.currentTimeMillis();
		try {
			r.open(new File("D:/graph/germany.txt"));
			while(r.hasNextNode())
			{
				n = r.nextNode();
				nodes++;
			}
			System.out.println("Nodes gelesen: "+nodes+" Nodes vorhanden: "+r.getNodeCount());
			while(r.hasNextEdge())
			{
				e = r.nextEdge();
				edges++;
			}
			System.out.println("Edges gelesen: "+edges+" Edges vorhanden: "+r.getEdgeCount());
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (NoGraphOpenException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (NoSuchElementException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		start = System.currentTimeMillis()-start;
		System.out.println(start+"Millisekunden ladezeit");
		

	}

}
