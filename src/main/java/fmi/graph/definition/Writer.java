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

import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.metaio.MetaData;

import java.io.File;
import java.io.IOException;

public interface Writer {

	public void create(File graph) throws IOException;
	
	public void createBin(File graph) throws IOException;
	
	public void setNodeCount(int n);
	
	public void setEdgeCount(int m);
	
	public void writeMetaData(MetaData data) throws IOException, InvalidFunctionException;
	
	public void writeNode(Node n) throws IOException, InvalidFunctionException;
	
	public void writeEdge (Edge e) throws IOException, InvalidFunctionException;
	
	public void close() throws InvalidFunctionException;
	
}
