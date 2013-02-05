package fmi.graph.definition;

public interface Node extends Comparable<Node>{

	public int getId();
	
	public long getOsmId();
	
	public double getLat();
	
	public double getLon();
	
	public int getElevation();
	
	public String toString();
	
}
