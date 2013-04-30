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

import java.io.DataOutputStream;
import java.io.IOException;

public interface Edge extends Comparable<Edge>{

	public int getSource();
	
	public int getTarget();
	
	public int getWeight();
	
	public int getType();
	
	public String toBaseString();
	
	public String toString();
	
	public void writeBin(DataOutputStream dos) throws IOException;

}
