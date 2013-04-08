package fmi.graph.definition;

import java.io.File;
import java.io.IOException;

import fmi.graph.exceptions.InvalidFunctionException;

public interface Writer {

	public void create(File graph) throws IOException;
	
	public void createBin(File graph) throws IOException;
	
	public void createGZip(File graph) throws IOException;
	
	public void setNodeCount(int n);
	
	public void setEdgeCount(int m);
	
	public void writeMetaData() throws IOException;
	
	public void writeNode(Node n) throws IOException, InvalidFunctionException;
	
	public void writeEdge (Edge e) throws IOException, InvalidFunctionException;
	
	public void close() throws InvalidFunctionException;
	
}
