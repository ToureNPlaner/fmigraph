package fmi.graph.standard;

import java.io.DataOutputStream;
import java.io.IOException;


public class Edge implements fmi.graph.definition.Edge{

	int source;
	int target;
	int weight;
	int type;
	String carryover;
	
	
	@SuppressWarnings("unused")
	private Edge() {
	}
	
	public Edge(int source, int target, int weight, int type)
	{
		this.source = source;
		this.target=target;
		this.weight=weight;
		this.type=type;
		this.carryover=null;
	}
	
	public Edge(int source, int target, int weight, int type, String carryover)
	{
		this.source = source;
		this.target=target;
		this.weight=weight;
		this.type=type;
		this.carryover=carryover;
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

	public String toBaseString()
	{
		return source+" "+target+" "+weight+" "+type;
	}
	
	public String toString()
	{
		if(carryover != null)
			return toBaseString()+" "+carryover;
		else
			return toBaseString();
	}

	@Override
	public void writeBin(DataOutputStream dos) throws IOException {
		dos.writeInt(source);
		dos.writeInt(target);
		dos.writeInt(weight);
		dos.writeInt(type);
	}
	
}
