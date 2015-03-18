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
import fmi.graph.metaio.MetaWriter;
import fmi.graph.metaio.Value;
import fmi.graph.tools.General;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public abstract class Writer {
    private static final Charset cset = Charset.forName("UTF-8");

    // Settings
    protected String type;
    protected int revision;

    boolean bin;

    int nodes;
    int edges;
    int nodesWritten;
    int edgesWritten;
    boolean headWritten;

    DataOutputStream dos;
    OutputStreamWriter bw;


    public Writer(File graph, boolean binary) throws IOException {
        this.bin = binary;
        nodesWritten = 0;
        edgesWritten = 0;
        headWritten = false;

        if (graph.exists())
            graph.delete();
        graph.createNewFile();
        if (bin) {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(graph)));
        } else {
            bw = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(graph)), cset);
        }
    }

    public Writer(OutputStream out, boolean binary) {
        bin = binary;

        nodesWritten = 0;
        edgesWritten = 0;
        headWritten = false;
        if(bin){
            dos = new DataOutputStream(out);
        } else {
            bw = new OutputStreamWriter(out, cset);
        }
    }

    public void setNodeCount(int n) {
        nodes = n;
    }

    public void setEdgeCount(int m) {
        edges = m;
    }

    public MetaData prepareMetaData() {
        MetaData data = new MetaData();
        return prepareMetaData(data);
    }

    public MetaData prepareMetaData(MetaData origin) {
        Iterator<Map.Entry<String, Value>> it = origin.entrySet().iterator();
        MetaData data = new MetaData();
        while (it.hasNext()) {
            Map.Entry<String, Value> e;
            e = it.next();
            String key = "Origin" + e.getKey();
            Value value = e.getValue();
            data.add(key, value);
            it.remove();
        }

        data.add("Id", General.createRandomIdValue());
        data.add("Timestamp", new Value(new Date()));
        data.add("Type", type);
        data.add("Revision", Integer.toString(revision));
        return data;
    }

    public void writeMetaData(MetaData data) throws IOException, InvalidFunctionException {
        if (headWritten)
            throw new InvalidFunctionException("Need to write Metadata before head/nodes/edges");

        MetaWriter w = new MetaWriter();
        if (bin) {
            w.writeMetaDataRaw(dos, data);
        } else {
            w.writeMetaDataWriter(bw, data);
        }
    }

    public void writeMetaData() throws IOException, InvalidFunctionException {
        if (headWritten)
            throw new InvalidFunctionException("Need to write Metadata before head/nodes/edges");

        MetaData data = prepareMetaData();

        MetaWriter w = new MetaWriter();
        if (bin) {
            w.writeMetaDataRaw(dos, data);
        } else {
            w.writeMetaDataWriter(bw, data);
        }
    }

    public void writeNode(Node n) throws IOException, GraphException {
        if (bin) {
            if (!headWritten) {
                dos.writeInt(nodes);
                dos.writeInt(edges);
                headWritten = true;
            }
            n.writeStream(dos);
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

        if (bin)
            e.writeStream(dos);
        else {
            bw.write(e.toString());
            bw.write('\n');
        }
        edgesWritten++;

    }

    public void close() throws InvalidFunctionException, IOException {
        if (bin)
            dos.close();
        else {
            bw.close();
        }

    }

}
