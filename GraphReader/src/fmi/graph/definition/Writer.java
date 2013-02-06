package fmi.graph.definition;

import java.io.File;
import java.io.IOException;

public interface Writer {

	public void open(File graph) throws IOException;
	
	public void setNodeCount();
	
	public void setEdgeCount();
	
	public void writeNode(Node n);
	
	public void writeEdge (Edge e);
	
	public void close();
	
}
