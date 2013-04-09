package fmi.graph;

import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;
import fmi.graph.metaio.MetaData;
import fmi.graph.metaio.Value;
import fmi.graph.standard.Edge;
import fmi.graph.standard.Node;
import fmi.graph.standard.Reader;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class ExampleReader {
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
			MetaData meta = r.openBin(new File("test.bin"));
            //MetaData meta = r.open(new File("test.txt"));
            //MetaData meta = r.openGZip(new File("test.gz"));
            for (Map.Entry<String, Value> entry : meta.data.entrySet()) {
                System.out.println("Key: \""+entry.getKey()+"\" with value: \""+entry.getValue().value+'"');
                System.out.println("Comments:");
                for (String comment : entry.getValue().comments){
                    System.out.println("\t"+comment);
                }
                System.out.println("---------------------------------------------------------------");
            }
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
