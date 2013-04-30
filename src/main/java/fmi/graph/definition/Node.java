package fmi.graph.definition;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Node extends Comparable<Node>{

	public int getId();
	
	public long getOsmId();
	
	public double getLat();
	
	public double getLon();
	
	public int getElevation();
	
	public String getCarryover();
	
	public String toBaseString();
	
	public String toString();
	
	public void writeBin(DataOutputStream dos) throws IOException;
	
}
