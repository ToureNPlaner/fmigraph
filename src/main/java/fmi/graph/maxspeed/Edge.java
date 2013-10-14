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

public class Edge extends fmi.graph.definition.Edge {

	int maxspeed;

	public Edge(int source, int target, int weight, int type, int maxspeed) {
		super(source, target, weight, type);
		this.maxspeed = maxspeed;
	}

	public Edge(int source, int target, int weight, int type, int maxspeed,
			String carryover) {
		super(source, target, weight, type, carryover);
		this.maxspeed = maxspeed;
	}

	public int getMaxspeed() {
		return maxspeed;
	}

	@Override
	public String toString() {
		
		if(carryover==null || carryover.length()==0)
			return source+" "+target+" "+weight+" "+type+" "+maxspeed;
		else
			return source+" "+target+" "+weight+" "+type+" "+maxspeed+" "+carryover;
	}

	@Override
	public void writeBin(DataOutputStream dos) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void parseLine(String line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readStream(DataInputStream dis) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
