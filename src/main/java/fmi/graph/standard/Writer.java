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

import fmi.graph.definition.Edge;
import fmi.graph.definition.Node;
import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.metaio.MetaData;
import fmi.graph.metaio.MetaWriter;
import fmi.graph.metaio.Value;
import fmi.graph.tools.General;
import java.io.*;
import java.util.Date;

public class Writer implements fmi.graph.definition.Writer {

	//Settings
	
	protected String type = "standard";
	protected int revision = 1;
	
	protected boolean order = true;
	protected boolean coherency = true;
	protected int startId = 0;
	
	//Internal Variables
	
	boolean bin;

	int nodes;
	int edges;
	int nodesWritten;
	int edgesWritten;
	boolean headWritten;

	DataOutputStream dos;
	BufferedWriter bw;

	public Writer()
	{
		type = "standard";
		revision = 3;
	}
	
	@Override
	public void create(File graph) throws IOException {
		if (graph.exists())
			graph.delete();
		graph.createNewFile();

		bin = false;
		bw = new BufferedWriter(new FileWriter(graph));
		nodesWritten = 0;
		edgesWritten = 0;
		headWritten = false;
	}

    @Override
    public void createBin(File graph) throws IOException {
        if (graph.exists()) graph.delete();
        graph.createNewFile();

        bin = true;
        dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(graph)));
        nodesWritten = 0;
        edgesWritten = 0;
        headWritten = false;
    }

    @Override
    public void write(OutputStream out) throws IOException {
        bin = false;
        bw = new BufferedWriter(new OutputStreamWriter(out));
        nodesWritten = 0;
        edgesWritten = 0;
        headWritten = false;
    }

	@Override
    public void writeBin(OutputStream out) throws IOException {
		bin = true;
		dos = new DataOutputStream(new BufferedOutputStream(out));
		nodesWritten = 0;
		edgesWritten = 0;
		headWritten = false;
	}

	@Override
	public void setNodeCount(int n) {
		nodes = n;
	}

	@Override
	public void setEdgeCount(int m) {
		edges = m;
	}

	@Override
	public void writeMetaData(MetaData data) throws IOException, InvalidFunctionException {
        if (headWritten)
            throw new InvalidFunctionException("Need to write Metadata before head/nodes/edges");
        
        //Add required MetaData Fields
        data.add("Id", General.createRandomIdValue());
        data.add("Timestamp", new Value(new Date()));
        data.add("Type", type);
        data.add("Revision", Integer.toString(revision));
        
        MetaWriter w = new MetaWriter();
        if (bin){
            w.writeMetaDataRaw(dos, data);
        } else {
            w.writeMetaDataWriter(bw, data);
        }
	}

	@Override
	public void writeNode(Node n) throws IOException, InvalidFunctionException {
		if (edgesWritten > 0 || nodes <= 0 || edges <= 0)
			throw new InvalidFunctionException();

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

	@Override
	public void writeEdge(Edge e) throws IOException, InvalidFunctionException {
		if(nodesWritten!=nodes)
			throw new InvalidFunctionException("To few Nodes written");
		if (bin)
			e.writeBin(dos);
		else {
			bw.write(e.toString());
			bw.write('\n');
		}
		edgesWritten++;

	}

	@Override
	public void close() throws InvalidFunctionException {
		if(nodes != nodesWritten || edges != edgesWritten)
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
