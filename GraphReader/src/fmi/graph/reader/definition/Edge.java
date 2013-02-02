package fmi.graph.reader.definition;

public interface Edge extends Comparable<Edge>{

	public int getSource();
	
	public int getTarget();
	
	public int getWeight();
	
	public int getType();
	
	public String toString();

}
