package fmi.graph.maxspeed;

public class Node extends fmi.graph.standard.Node {

	public Node(int id, long osm, double lat, double lon, int elevation)
	{
			super(id, osm, lat, lon, elevation);
	}
	
	public Node(int id, long osm, double lat, double lon, int elevation, String carryover)
	{
		super(id, osm, lat, lon, elevation,carryover);
	}
}
