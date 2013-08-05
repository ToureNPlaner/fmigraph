/*
 * Copyright 2013 FMI Universität Stuttgart
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package fmi.graph.standard;

import java.io.DataOutputStream;
import java.io.IOException;

public class Node implements fmi.graph.definition.Node{

	int id;
	long osm;
	double lat;
	double lon;
	int elevation;
	String carryover;
	
	public Node()
	{
		
	}
	
	public Node(int id, long osm, double lat, double lon, int elevation)
	{
		this.id=id;
		this.osm=osm;
		this.lat=lat;
		this.lon=lon;
		this.elevation = elevation;
		this.carryover = null;
	}
	
	public Node(int id, long osm, double lat, double lon, int elevation, String carryover)
	{
		this.id=id;
		this.osm=osm;
		this.lat=lat;
		this.lon=lon;
		this.elevation = elevation;
		this.carryover = carryover;
	}
	
	
	public int compareTo(fmi.graph.definition.Node o) {
		
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
	
	public String getCarryover()
	{
		return carryover;
	}

	@Override
	public String toBaseString() {
		return id+ " "+osm+" "+lat+" "+lon+" "+elevation; 
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
		dos.writeInt(id);
		dos.writeLong(osm);
		dos.writeDouble(lat);
		dos.writeDouble(lon);
		dos.writeInt(elevation);
		
	}

}
