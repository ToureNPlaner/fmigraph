package fmi.graph.standard.reader;


public class Edge implements fmi.graph.definition.Edge{

	private int source;
	private int target;
	private int weight;
	private int type;
	
	
	
	@SuppressWarnings("unused")
	private Edge() {
	}
	
	public Edge(int source, int target, int weight, int type)
	{
		this.source = source;
		this.target=target;
		this.weight=weight;
		this.type=type;
	}
	
	
	public int compareTo (fmi.graph.definition.Edge e) {
		if(source < e.getSource())
			return -1;
		if (source > e.getSource())
			return 1;
		if(target<e.getTarget())
			return -1;
		if(target>e.getTarget())
			return 1;
		if(weight<e.getWeight())
			return -1;
		if(weight>e.getWeight())
			return 1;
		
		return 0;
	}

	@Override
	public int getSource() {
		return source;
	}

	@Override
	public int getTarget() {
		return target;
	}


	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public int getType() {
		return type;
	}

	public String toString()
	{
		return source+" "+target+" "+weight+" "+type;
	}
	
}
