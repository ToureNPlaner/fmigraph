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

import fmi.graph.exceptions.CoherencyException;
import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.exceptions.OrderException;
import fmi.graph.exceptions.StartIdException;
import fmi.graph.metaio.MetaData;
import fmi.graph.metaio.MetaWriter;
import fmi.graph.metaio.Value;
import fmi.graph.tools.General;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public abstract class Writer {

	// Settings
	protected String type;
	protected int revision;

	protected boolean enforceStructure;

	protected boolean order;
	protected boolean coherency;
	protected int startId;

	// Internal Variables

	boolean bin;

	int nodes;
	int edges;
	int nodesWritten;
	int edgesWritten;
	boolean headWritten;

	Node n;
	Edge e;

	DataOutputStream dos;
	BufferedWriter bw;

	public void create(File graph) throws IOException {
		bin = false;
		nodesWritten = 0;
		edgesWritten = 0;
		headWritten = false;
		
		if (graph.exists())
			graph.delete();
		graph.createNewFile();
		
		bw = new BufferedWriter(new FileWriter(graph));
		
	}

	public void createBin(File graph) throws IOException {
		if (graph.exists())
			graph.delete();
		graph.createNewFile();

		bin = true;
		dos = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(graph)));
		nodesWritten = 0;
		edgesWritten = 0;
		headWritten = false;
	}

	public void write(OutputStream out) throws IOException {
		bin = false;
		bw = new BufferedWriter(new OutputStreamWriter(out));
		nodesWritten = 0;
		edgesWritten = 0;
		headWritten = false;
	}

	public void writeBin(OutputStream out) throws IOException {
		bin = true;
		dos = new DataOutputStream(new BufferedOutputStream(out));
		nodesWritten = 0;
		edgesWritten = 0;
		headWritten = false;
	}

	public void setNodeCount(int n) {
		nodes = n;
	}

	public void setEdgeCount(int m) {
		edges = m;
	}

	
	
	public MetaData prepareMetaData()
	{
		MetaData data = new MetaData();
		data.add("Id", General.createRandomIdValue());
		data.add("Timestamp", new Value(new Date()));
		data.add("Type", type);
		data.add("Revision", Integer.toString(revision));
		return data;
	}
	
	public void writeMetaData(MetaData data) throws IOException,	InvalidFunctionException {
		if (headWritten)
			throw new InvalidFunctionException(	"Need to write Metadata before head/nodes/edges");

		MetaWriter w = new MetaWriter();
		if (bin) {
			w.writeMetaDataRaw(dos, data);
		} else {
			w.writeMetaDataWriter(bw, data);
		}
	}
	
	public void writeMetaData() throws IOException,	InvalidFunctionException {
		if (headWritten)
			throw new InvalidFunctionException(	"Need to write Metadata before head/nodes/edges");

		MetaData data = prepareMetaData();
		
		MetaWriter w = new MetaWriter();
		if (bin) {
			w.writeMetaDataRaw(dos, data);
		} else {
			w.writeMetaDataWriter(bw, data);
		}
	}
	
	
	
	
	

	public void writeNode(Node n) throws IOException, GraphException {
		if (edgesWritten > 0 || nodes <= 0 || edges <= 0)
			throw new InvalidFunctionException();

		if(enforceStructure)
		{
			if(this.n==null)
			{
				if(n.getId()!=startId)
					throw new StartIdException("Expected Id: "+startId+" got: "+n.getId());
			}
			else
			{
				if(this.n.getId()+1!=n.getId())
					throw new CoherencyException("Expected nodeId: "+(this.n.getId()+1)+" got: "+n.getId());
			}
		}
		this.n=n;
		
		
		if (bin) {
			if (!headWritten) {
				dos.writeInt(nodes);
				dos.writeInt(edges);
				headWritten = true;
			}
			n.writeBin(dos);
		} else {
			if (!headWritten) {
				bw.write(Integer.toString(nodes));
				bw.write('\n');
				bw.write(Integer.toString(edges));
				bw.write('\n');
				headWritten = true;
			}
			bw.write(n.toString());
			bw.write('\n');
		}
		nodesWritten++;

	}

	public void writeEdge(Edge e) throws IOException, GraphException {
		if (nodesWritten != nodes)
			throw new InvalidFunctionException("To few Nodes written");
		
		if(enforceStructure)
		{
			if(e.getSource()<startId||e.getSource()>n.getId())
				throw new GraphException("Edge Source out of bounds: "+e.getSource());
			if(e.getTarget()<startId||e.getTarget()>n.getId())
				throw new GraphException("Edge Target out of bounds: "+e.getTarget());
			if(this.e!=null)
			{
				if(this.e.getSource()>e.getSource())
					throw new OrderException("Edge not in correct order: "+ e.getSource()+":"+e.getTarget());
				if((this.e.getSource()==e.getSource())&&(this.e.getTarget()>e.getTarget()))
					throw new OrderException("Edge Target not in correct order: "+ e.getSource()+":"+e.getTarget());
			}
		}
		this.e=e;
		
		if (bin)
			e.writeBin(dos);
		else {
			bw.write(e.toString());
			bw.write('\n');
		}
		edgesWritten++;

	}

	public void close() throws InvalidFunctionException {
		if (nodes != nodesWritten || edges != edgesWritten)
			throw new InvalidFunctionException("Too few edges or nodes written");
		if (bin)
			try {
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
