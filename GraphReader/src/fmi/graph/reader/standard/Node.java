package fmi.graph.reader.standard;

public class Node implements fmi.graph.reader.definition.Node{

	int id;
	long osm;
	double lat;
	double lon;
	int elevation;
	
	
	@SuppressWarnings("unused")
	private Node()
	{
		
	}
	
	public Node(int id, long osm, double lat, double lon, int elevation)
	{
		this.id=id;
		this.osm=osm;
		this.lat=lat;
		this.lon=lon;
		this.elevation=elevation;
	}
	
	
	public int compareTo(fmi.graph.reader.definition.Node o) {
		
		if(id<o.getId())
			return -1;
		if(id>o.getId())
			return 1;
		return 0;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public long getOsmId() {
		return osm;
	}

	@Override
	public double getLat() {
		return lat;
	}

	@Override
	public double getLon() {
		return lon;
	}

	@Override
	public int getElevation() {
		return elevation;
	}

	public String toString()
	{
		return id+ " "+osm+" "+lat+" "+lon+" "+elevation; 
	}

}
