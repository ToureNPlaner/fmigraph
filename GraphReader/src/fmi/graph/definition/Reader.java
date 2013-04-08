package fmi.graph.definition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;


public interface Reader {
	
	/**
	 * Opens a text based graph of the format defined at: 
	 * @param graph
	 * @throws IOException
	 */
	public void open(File graph) throws IOException;
	
	/**
	 * Opens a text based graph of the format defined at: 
	 * @param in
	 * @throws IOException
	 */
	public void read(InputStream in) throws IOException;
	
	/**
	 * Opens a binary graph of the format defined at: 
	 * @param graph
	 * @throws IOException
	 */
	public void openBin(File graph) throws IOException;
	
	/**
	 * Opens a zipped binary graph of the format defined at: 
	 * @param graph
	 * @throws IOException
	 */
	public void openGZip(File graph) throws IOException;
	
	/**
	 * Opens a binary graph of the format defined at: 
	 * @param graph
	 * @throws IOException
	 */
	public void readBin(InputStream in) throws IOException;
	
	/**
	 * Returns the node count of the selected graph
	 * @return
	 * @throws NoGraphOpenException
	 */
	public int getNodeCount() throws NoGraphOpenException;
	
	/**
	 * Returns the edge count of the selected graph
	 * @return
	 * @throws NoGraphOpenException
	 */
	public int getEdgeCount() throws NoGraphOpenException;
	
	/**
	 * Returns true if a node can be read
	 * @return
	 * @throws NoGraphOpenException
	 */
	public boolean hasNextNode();
	
	/**
	 * Returns true if an edge can be read
	 * @return
	 * @throws NoGraphOpenException
	 */
	public boolean hasNextEdge();
	
	/**
	 * Returns the next Node
	 * @return
	 * @throws NoGraphOpenException
	 */
	public Node nextNode() throws NoGraphOpenException, NoSuchElementException;
	
	/**
	 * Returns the next Edge
	 * @return
	 * @throws NoGraphOpenException
	 */
	public Edge nextEdge() throws NoGraphOpenException, NoSuchElementException;
	
	/**
	 * Closes all created readers and streams
	 * @return
	 * @throws NoGraphOpenException
	 */
	public void close();

}
