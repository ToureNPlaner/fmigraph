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
