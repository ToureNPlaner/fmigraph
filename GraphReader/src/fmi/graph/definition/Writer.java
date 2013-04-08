package fmi.graph.definition;

import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.metaio.MetaData;

import java.io.File;
import java.io.IOException;

public interface Writer {

	public void create(File graph) throws IOException;
	
	public void createBin(File graph) throws IOException;
	
	public void createGZip(File graph) throws IOException;
	
	public void setNodeCount(int n);
	
	public void setEdgeCount(int m);
	
	public void writeMetaData(MetaData data) throws IOException, InvalidFunctionException;
	
	public void writeNode(Node n) throws IOException, InvalidFunctionException;
	
	public void writeEdge (Edge e) throws IOException, InvalidFunctionException;
	
	public void close() throws InvalidFunctionException;
	
}
