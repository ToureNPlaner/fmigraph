package fmi.graph.maxspeed;

public class Node extends fmi.graph.standard.Node {

	public Node(int id, long osm, Double lat, Double lon, int elevation) {
		super(id, osm, lat, lon, elevation);
	}
	
	public Node(int id, long osm, Double lat, Double lon, String carryover, int elevation) {
		super(id, osm, lat, lon, elevation, carryover);
	}

}
