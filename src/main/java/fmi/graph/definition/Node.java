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
package fmi.graph.definition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Node implements Comparable<Node> {

	protected int id;
	protected long osm;
	protected double lat;
	protected double lon;
	protected int elevation;
	protected String carryover;

	public Node(String line) {
		parseLine(line);
	}

	public Node(DataInputStream dis) throws IOException {
		readStream(dis);
	}

	public Node(int id, long osm, double lat, double lon, int elevation) {
		this.id = id;
		this.osm = osm;
		this.lat = lat;
		this.lon = lon;
		this.elevation = elevation;
		this.carryover = null;
	}

	public Node(int id, long osm, double lat, double lon, int elevation, String carryover) {
		this.id = id;
		this.osm = osm;
		this.lat = lat;
		this.lon = lon;
		this.elevation = elevation;
		this.carryover = carryover;
	}

	public int compareTo(Node o) {

		if (id < o.getId())
			return -1;
		if (id > o.getId())
			return 1;
		return 0;
	}

	public int getId() {
		return id;
	}

	public long getOsmId() {
		return osm;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public int getElevation() {
		return elevation;
	}

	public String getCarryover() {
		return carryover;
	}

	public abstract String toString();

	public abstract void writeStream(DataOutputStream dos) throws IOException;

	protected abstract void parseLine(String line);

	protected abstract void readStream(DataInputStream dis) throws IOException;

}
