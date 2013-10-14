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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Node extends fmi.graph.definition.Node {

	public Node(DataInputStream dis) throws IOException {
		super(dis);
	}

	public Node(String line) {
		super(line);
	}

	public Node(int id, long osm, double lat, double lon, int elevation) {
		super(id, osm, lat, lon, elevation);
	}

	public Node(int id, long osm, double lat, double lon, int elevation, String carryover) {
		super(id, osm, lat, lon, elevation, carryover);
	}

	@Override
	protected void parseLine(String line) {
		String[] split = line.split(" ", 6);
		this.id = Integer.parseInt(split[0]);
		this.osm = Long.parseLong(split[1]);
		this.lat = Double.parseDouble(split[2]);
		this.lon = Double.parseDouble(split[3]);
		this.elevation = Integer.parseInt(split[4]);
		if (split.length == 6)
			this.carryover = split[5];
		else
			this.carryover = null;
	}

	@Override
	public String toString() {
		if (carryover != null && carryover.length() > 0)
			return id + " " + osm + " " + lat + " " + lon + " " + elevation + " " + carryover;
		else
			return id + " " + osm + " " + lat + " " + lon + " " + elevation;
	}

	@Override
	protected void readStream(DataInputStream dis) throws IOException {
		this.id = dis.readInt();
		this.osm = dis.readLong();
		this.lat = dis.readDouble();
		this.lon = dis.readDouble();
		this.elevation = dis.readInt();
		int carryLength = dis.readInt();

		if (carryLength > 0) {
			byte[] b = new byte[carryLength];
			dis.read(b);
			this.carryover = new String(b, Charset.forName("UTF-8"));
		} else
			carryover = null;
	}

	@Override
	public void writeStream(DataOutputStream dos) throws IOException {
		dos.writeInt(this.id);
		dos.writeLong(this.osm);
		dos.writeDouble(this.lat);
		dos.writeDouble(this.lon);
		dos.writeInt(this.elevation);

		int carrysize = 0;
		byte[] bCarryover = null;
		if (carryover != null && carryover.length() > 0) {
			bCarryover = carryover.getBytes(Charset.forName("UTF-8"));
			carrysize = bCarryover.length;
		}
		dos.writeInt(carrysize);

		if (carrysize > 0)
			dos.write(bCarryover);

	}
}
