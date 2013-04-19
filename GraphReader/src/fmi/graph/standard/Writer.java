package fmi.graph.standard;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import fmi.graph.definition.Node;
import fmi.graph.definition.Edge;
import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.metaio.MetaData;
import fmi.graph.metaio.MetaWriter;

import java.io.*;

public class Writer implements fmi.graph.definition.Writer {

	boolean bin;

	int type = 1;
	int revision = 1;

	int nodes;
	int edges;
	int nodesWritten;
	int edgesWritten;
	boolean headWritten;

	DataOutputStream dos;
	BufferedWriter bw;

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
				bw.newLine();
				bw.write(Integer.toString(edges));
				bw.newLine();
				headWritten = true;
			}
			bw.write(n.toString());
			bw.newLine();
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
			bw.newLine();
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
