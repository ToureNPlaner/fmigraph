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

import fmi.graph.tools.SaneBufferedInputStream;
import fmi.graph.exceptions.InvalidGraphTypeException;
import fmi.graph.exceptions.MissingMetadataException;
import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;
import fmi.graph.metaio.MetaData;
import fmi.graph.metaio.MetaReader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Reader {

	// Stettings
	public boolean order = true;
	public boolean coherency = true;
	public int startId = 0;
	public boolean enforceStructure = true;
	public boolean enforceMetadata = false;

	// intern Variables
	protected boolean bin = false;

	protected int nodes;
	protected int edges;
	protected int nodesRead = 0;
	protected int edgesRead = 0;

	protected DataInputStream dis = null;
	protected SaneBufferedInputStream bis = null;
	protected BufferedReader br = null;

	protected Node n;
	protected Edge e;

	public Reader() {

	}

	public Reader(boolean enforceStructure, boolean enforceMetadata) {
		this.enforceMetadata = enforceMetadata;
		this.enforceStructure = enforceStructure;
	}

	public MetaData open(File graph) throws IOException, GraphException {
		bin = false;
		br = new BufferedReader(new FileReader(graph));
		return readHead();
	}

	public MetaData  openBin(File graph) throws IOException, GraphException {
		bin = true;
		bis = new SaneBufferedInputStream(new FileInputStream(graph));
		return readHead();
	}

	public MetaData read(InputStream in) throws IOException, GraphException {
		bin = false;
		br = new BufferedReader(new InputStreamReader(in));
		return readHead();
	}

	public MetaData readBin(InputStream in) throws IOException, GraphException {
		bin = true;
		bis = new SaneBufferedInputStream(in);
		return readHead();
	}

	public int getNodeCount() throws NoGraphOpenException {

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		return nodes;
	}

	public int getEdgeCount() throws NoGraphOpenException {

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		return edges;
	}

	public boolean hasNextNode() {
		if (br == null && dis == null)
			return false;

		if (nodesRead < nodes)
			return true;
		else
			return false;
	}

	public boolean hasNextEdge() {
		if (br == null && dis == null)
			return false;

		if (nodesRead == nodes && edgesRead < edges)
			return true;
		else
			return false;

	}

	protected abstract Node readNodeBin() throws IOException;

	protected abstract Node readNodeString(String line) throws NoSuchElementException;

	public Node nextNode() throws NoGraphOpenException, GraphException {
		Node n = null;

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		if (nodesRead >= nodes)
			throw new NoSuchElementException();

		if (bin) {
			nodesRead++;
			try {
				n = readNodeBin();
			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		} else {
			String line;
			try {
				while (true) {
					line = br.readLine().trim();
					if (line.charAt(0) != '#')
						break;
				}
				nodesRead++;
				n = readNodeString(line);

			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}

		if (enforceStructure) {
			validateNode(n);
		}
		return n;
	}

	protected abstract Edge readEdgeBin() throws IOException;

	protected abstract Edge readEdgeString(String line) throws NoSuchElementException;

	public Edge nextEdge() throws NoGraphOpenException, GraphException {

		Edge e;

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		if (edgesRead >= edges)
			throw new NoSuchElementException();

		if (bin) {
			try {
				edgesRead++;
				e = readEdgeBin();
			} catch (IOException ex) {
				throw new NoSuchElementException(ex.getMessage());
			}
		} else {
			String line;

			try {
				while (true) {
					line = br.readLine().trim();
					if (line.charAt(0) != '#')
						break;
				}
				edgesRead++;
				e = readEdgeString(line);
			} catch (IOException ex) {
				throw new NoSuchElementException(ex.getMessage());
			}
		}

		if (enforceStructure) {
			validateEdge(e);
		}
		this.e = e;
		return e;
	}

	public void close() {
		try {
			if (br != null)
				br.close();
			else
				dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected abstract boolean validGraphType(String type);

	protected abstract boolean validGraphRevision(String type, String revision);

	protected abstract void validateNode(Node n) throws GraphException;

	protected abstract void validateEdge(Edge e) throws GraphException;

	protected void validateMetaData(MetaData m) throws GraphException {
		if (m.get("Type") == null)
			throw new MissingMetadataException("No Graph Type specified");
		if (m.get("Revision") == null)
			throw new MissingMetadataException("No Graph Type Revision specified");
		if (m.get("Id") == null)
			throw new MissingMetadataException("No Id specified");
		if (m.get("Timestamp") == null)
			throw new MissingMetadataException("No Timestamp specified");
		if (!validGraphType(m.get("Type").toString()))
			throw new InvalidGraphTypeException("Invalid Graph Format given");
		if (!validGraphRevision(m.get("Type").toString(), m.get("Revision").toString()))
			throw new InvalidGraphTypeException("Invalid Graph Format Revision given");

	}

	private MetaData readHead() throws IOException, GraphException {
		MetaReader mr = new MetaReader();
		MetaData meta = null;
		nodes = -1;
		nodesRead = 0;
		edges = -1;
		edgesRead = 0;

		if (bin) {
			meta = mr.readMetaData(bis);
			dis = new DataInputStream(bis);
			nodes = dis.readInt();
			edges = dis.readInt();
		} else {
			meta = mr.readMetaData(br);
			while (true) {
				String line;
				line = br.readLine().trim();
				if (line.charAt(0) == '#')
					continue;
				if (nodes == -1)
					nodes = Integer.parseInt(line);
				else if (edges == -1) {
					edges = Integer.parseInt(line);
					break;
				}

			}
		}

		if (enforceMetadata) {
			validateMetaData(meta);
		}

		return meta;
	}

}
