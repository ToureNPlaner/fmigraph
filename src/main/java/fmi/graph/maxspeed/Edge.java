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
package fmi.graph.maxspeed;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Edge extends fmi.graph.definition.Edge {

	int maxspeed;

	public Edge(DataInputStream dis) throws IOException {
		super(dis);
	}

	public Edge(String line) {
		super(line);
	}

	public Edge(int source, int target, int weight, int type, int maxspeed) {
		super(source, target, weight, type);
		this.maxspeed = maxspeed;
	}

	public Edge(int source, int target, int weight, int type, int maxspeed, String carryover) {
		super(source, target, weight, type, carryover);
		this.maxspeed = maxspeed;
	}

	public int getMaxspeed() {
		return maxspeed;
	}

	@Override
	public String toString() {

		if (carryover == null || carryover.length() == 0)
			return source + " " + target + " " + weight + " " + type + " " + maxspeed;
		else
			return source + " " + target + " " + weight + " " + type + " " + maxspeed + " " + carryover;
	}

	@Override
	public void writeBin(DataOutputStream dos) throws IOException {
		dos.writeInt(this.source);
		dos.writeInt(this.target);
		dos.writeInt(this.weight);
		dos.writeInt(this.type);
		dos.writeInt(this.maxspeed);

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

	@Override
	protected void parseLine(String line) {
		String[] split = line.split(" ", 6);
		this.source = Integer.parseInt(split[0]);
		this.target = Integer.parseInt(split[1]);
		this.weight = Integer.parseInt(split[2]);
		this.type = Integer.parseInt(split[3]);
		this.maxspeed = Integer.parseInt(split[4]);
		if (split.length == 6)
			this.carryover = split[5];
		else
			this.carryover = null;

	}

	@Override
	protected void readStream(DataInputStream dis) throws IOException {
		this.source = dis.readInt();
		this.target = dis.readInt();
		this.weight = dis.readInt();
		this.type = dis.readInt();
		this.maxspeed = dis.readInt();
		int carryLength = dis.readInt();

		if (carryLength > 0) {
			byte[] b = new byte[carryLength];
			dis.read(b);
			this.carryover = new String(b, Charset.forName("UTF-8"));
		} else
			carryover = null;
	}

}
