package fmi.graph.maxspeed;

public class Edge extends fmi.graph.standard.Edge {

	int maxspeed;
	
	public Edge(int source, int target, int weight, int type, int maxspeed) {
		super(source, target, weight, type);
		this.maxspeed = maxspeed;
	}

	public Edge(int source, int target, int weight, int type, int maxspeed, String carryover) {
		super(source, target, weight, type, carryover);
		this.maxspeed = maxspeed;
	}
	
	public int getMaxspeed()
	{
		return maxspeed;
	}
	
	public String toBaseString()
	{
		return super.toBaseString()+" "+maxspeed;
	}

}
