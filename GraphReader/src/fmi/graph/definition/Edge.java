package fmi.graph.definition;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Edge extends Comparable<Edge>{

	public int getSource();
	
	public int getTarget();
	
	public int getWeight();
	
	public int getType();
	
	public String toBaseString();
	
	public String toString();
	
	public void writeBin(DataOutputStream dos) throws IOException;

}
