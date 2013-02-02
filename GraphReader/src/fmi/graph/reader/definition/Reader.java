package fmi.graph.reader.definition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import fmi.graph.reader.exceptions.NoGraphOpenException;
import fmi.graph.reader.exceptions.NoSuchElementException;


public interface Reader {
	
	public void open(File graph) throws IOException;
	
	void read(InputStream in) throws IOException;
	
	public int getNodeCount() throws NoGraphOpenException;
	
	public int getEdgeCount() throws NoGraphOpenException;
	
	public boolean hasNextNode();
	
	public boolean hasNextEdge();
	
	public Node nextNode() throws NoGraphOpenException, NoSuchElementException;
	
	public Edge nextEdge() throws NoGraphOpenException, NoSuchElementException;

}
