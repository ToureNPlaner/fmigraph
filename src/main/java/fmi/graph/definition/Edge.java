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

public abstract class Edge implements Comparable<Edge> {

	protected int source;
	protected int target;
	protected int weight;
	protected int type;
	protected String carryover;

	public Edge(String line) {
		parseLine(line);
	}

	public Edge(DataInputStream dis) throws IOException {
		readStream(dis);
	}

	public Edge(int source, int target, int weight, int type) {
		this.source = source;
		this.target = target;
		this.weight = weight;
		this.type = type;
	}

	public Edge(int source, int target, int weight, int type, String carryover) {
		this.source = source;
		this.target = target;
		this.weight = weight;
		this.type = type;
		this.carryover = carryover;
	}

	public int getSource() {
		return source;
	}

	public int getTarget() {
		return target;
	}

	public int getWeight() {
		return weight;
	}

	public int getType() {
		return type;
	}

	public String getCarryover() {
		return carryover;
	}

	public int compareTo(Edge o) {

		if (source < o.getSource())
			return -1;
		if (source > o.getSource())
			return 1;
		if (target < o.getTarget())
			return -1;
		if (target > o.getTarget())
			return 1;
		if (type < o.getType())
			return -1;
		if (type > o.getType())
			return 1;
		return 0;
	}

	public abstract String toString();

	public abstract void writeStream(DataOutputStream dos) throws IOException;

	protected abstract void parseLine(String line);

	protected abstract void readStream(DataInputStream dis) throws IOException;

}
