package fmi.graph.reader.maxspeed;

public class Edge extends fmi.graph.reader.standard.Edge {

	int maxspeed;
	
	public Edge(int source, int target, int weight, int type, int maxspeed) {
		super(source, target, weight, type);
		this.maxspeed = maxspeed;
	}

	public int getMaxspeed()
	{
		return maxspeed;
	}
	
	public String toString()
	{
		return super.toString()+" "+maxspeed;
	}

}
