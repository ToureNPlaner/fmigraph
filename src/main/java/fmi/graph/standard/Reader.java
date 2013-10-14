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

import fmi.graph.definition.GraphException;
import fmi.graph.exceptions.CoherencyException;
import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;
import fmi.graph.exceptions.OrderException;
import fmi.graph.exceptions.StartIdException;

import java.io.*;

public class Reader extends fmi.graph.definition.Reader {

	public Reader() {
		super();
		order = true;
		coherency = true;
		startId = 0;
		enforceStructure = true;
		enforceMetadata = false;
	}

	@Override
	public Node nextNode() throws NoGraphOpenException, GraphException {
		return (Node) super.nextNode();
	}

	@Override
	public Edge nextEdge() throws NoGraphOpenException, GraphException {
		return (Edge) super.nextEdge();
	}

	@Override
	protected Node readNodeBin() throws IOException {
		return new Node(dis);
	}

	@Override
	protected Node readNodeString(String line) throws NoSuchElementException {
		return new Node(line);
	}

	@Override
	protected Edge readEdgeBin() throws IOException {
		return new Edge(dis);
	}

	@Override
	protected Edge readEdgeString(String line) throws NoSuchElementException {
		return new Edge(line);
	}

	@Override
	protected boolean validGraphType(String type) {
		if (bin)
			return type.compareTo("standard") == 0;
		else
			return true;
	}

	@Override
	protected boolean validGraphRevision(String type, String revision) {
		return true;
	}

	@Override
	protected void validateNode(fmi.graph.definition.Node n) throws GraphException {
		if (this.n == null) {
			if (n.getId() != this.startId)
				throw new StartIdException("Excpected StartId: " + this.startId + " got: " + n.getId());
		} else {
			if (this.n.getId() + 1 != n.getId())
				throw new CoherencyException("Expected NodeId: " + (this.n.getId() + 1) + " got: " + n.getId());
		}
		this.n = n;

	}

	@Override
	protected void validateEdge(fmi.graph.definition.Edge e) throws GraphException {

		if (e.getSource() < startId || e.getSource() > n.getId())
			throw new GraphException("Edge Source out of bounds: " + e.getSource());
		if (e.getTarget() < startId || e.getTarget() > n.getId())
			throw new GraphException("Edge Target out of bounds: " + e.getTarget());
		if (this.e == null)
			this.e = e;
		else {
			if (this.e.getSource() > e.getSource())
				throw new OrderException("Edge not in correct order: " + e.getSource() + ":" + e.getTarget());
			if ((this.e.getSource() == e.getSource()) && (this.e.getTarget() > e.getTarget()))
				throw new OrderException("Edge Target not in correct order: " + e.getSource() + ":" + e.getTarget());
		}
		this.e = e;

	}

}
