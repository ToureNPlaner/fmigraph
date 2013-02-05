package fmi.graph.definition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;


public interface Reader {
	
	public void open(File graph) throws IOException;
	
	public void read(InputStream in) throws IOException;
	
	public int getNodeCount() throws NoGraphOpenException;
	
	public int getEdgeCount() throws NoGraphOpenException;
	
	public boolean hasNextNode();
	
	public boolean hasNextEdge();
	
	public Node nextNode() throws NoGraphOpenException, NoSuchElementException;
	
	public Edge nextEdge() throws NoGraphOpenException, NoSuchElementException;
	
	public void close();

}
