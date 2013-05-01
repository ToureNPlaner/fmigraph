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

import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;
import fmi.graph.standard.Node;
import fmi.graph.metaio.MetaData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public interface Reader {
	
	/**
	 * Opens a text based graph of the format defined at: 
	 *
     * @param graph
     * @throws IOException
	 */
	public MetaData open(File graph) throws IOException;
	
	/**
	 * Opens a text based graph of the format defined at: 
	 *
     * @param in
     * @throws IOException
	 */
	public MetaData read(InputStream in) throws IOException;
	
	/**
	 * Opens a binary graph of the format defined at: 
	 *
     * @param graph
     * @throws IOException
	 */
	public MetaData openBin(File graph) throws IOException;
	

	/**
	 * Opens a binary graph of the format defined at: 
	 * @throws IOException
	 */
	public MetaData readBin(InputStream in) throws IOException;
	
	/**
	 * Returns the node count of the selected graph
	 * @return
	 * @throws NoGraphOpenException
	 */
	public int getNodeCount() throws NoGraphOpenException;
	
	/**
	 * Returns the edge count of the selected graph
	 * @return
	 * @throws NoGraphOpenException
	 */
	public int getEdgeCount() throws NoGraphOpenException;
	
	/**
	 * Returns true if a node can be read
	 * @return
	 * @throws NoGraphOpenException
	 */
	public boolean hasNextNode();
	
	/**
	 * Returns true if an edge can be read
	 * @return
	 * @throws NoGraphOpenException
	 */
	public boolean hasNextEdge();
	
	/**
	 * Returns the next Node
	 * @return
	 * @throws NoGraphOpenException
	 */
	public Node nextNode() throws NoGraphOpenException, NoSuchElementException;
	
	/**
	 * Returns the next Edge
	 * @return
	 * @throws NoGraphOpenException
	 */
	public Edge nextEdge() throws NoGraphOpenException, NoSuchElementException;
	
	/**
	 * Closes all created readers and streams
	 * @return
	 * @throws NoGraphOpenException
	 */
	public void close();

}
