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
import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;
import fmi.graph.metaio.MetaData;
import fmi.graph.metaio.MetaReader;
import fmi.graph.tools.SaneBufferedInputStream;

import java.io.*;
import java.nio.charset.Charset;

public class Reader implements fmi.graph.definition.Reader {

	//Settings
	
	protected boolean order = true;
	protected boolean coherency = true;
	protected int startId = 0;
	

	//intern Variables
	protected boolean bin = false;

	protected int nodes;
	protected int edges;
	protected int nodesRead = 0;
	protected int edgesRead = 0;
	
	protected DataInputStream dis = null;
    protected SaneBufferedInputStream bis = null;
	protected BufferedReader br = null;

	@Override
	public MetaData open(File graph) throws IOException, GraphException {
		bin = false;
		br = new BufferedReader(new FileReader(graph));
		return readHead();
	}

	@Override
	public MetaData openBin(File graph) throws IOException, GraphException {
		bin = true;
        bis = new SaneBufferedInputStream(new FileInputStream(graph));
		return readHead();
	}

	@Override
	public MetaData read(InputStream in) throws IOException, GraphException {
		bin = false;
		br = new BufferedReader(new InputStreamReader(in));
		return readHead();
	}

	@Override
	public MetaData readBin(InputStream in) throws IOException, GraphException {
		bin = true;
		dis = new DataInputStream(new SaneBufferedInputStream(in));
		return readHead();
	}

	@Override
	public int getNodeCount() throws NoGraphOpenException {

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		return nodes;
	}

	@Override
	public int getEdgeCount() throws NoGraphOpenException {

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		return edges;
	}

	@Override
	public boolean hasNextNode() {
		if (br == null && dis == null)
			return false;

		if (nodesRead < nodes)
			return true;
		else
			return false;
	}

	@Override
	public boolean hasNextEdge() {
		if (br == null && dis == null)
			return false;

		if (nodesRead == nodes && edgesRead < edges)
			return true;
		else
			return false;

	}

    protected Node readNodeBin() throws IOException {
        int id = dis.readInt();
        long osmId = dis.readLong();
        double lat = dis.readDouble();
        double lon = dis.readDouble();
        int elevation = dis.readInt();
        int carryLength = dis.readInt();
        
        if(carryLength==0)
        {
        	return new Node(id, osmId, lat, lon, elevation);
        }
        
        byte[] b = new byte[carryLength];
        dis.read(b);
    	String carryover = new String(b, Charset.forName("UTF-8"));
    	
    	return new Node(id, osmId, lat, lon, elevation, carryover);
    }

    protected Node readNodeString(String line) throws NoSuchElementException {
        String[] split = line.split(" ",6);
        if(split.length==6)
        {
            return new Node(Integer.parseInt(split[0]),	Long.parseLong(split[1]), Double.parseDouble(split[2]),
                    Double.parseDouble(split[3]), Integer.parseInt(split[4]),split[5]);
        }
        else if(split.length==5)
        {
            return new Node(Integer.parseInt(split[0]),	Long.parseLong(split[1]), Double.parseDouble(split[2]),
                    Double.parseDouble(split[3]), Integer.parseInt(split[4]));
        }
        else
        {
            throw new NoSuchElementException("Malformed node:"+line);
        }
    }

	@Override
	public Node nextNode() throws NoGraphOpenException, NoSuchElementException {

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		if (nodesRead >= nodes)
			throw new NoSuchElementException();

		if (bin) {
			nodesRead++;
			try {
				return readNodeBin();
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
                return readNodeString(line);

			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}
	}

    protected Edge readEdgeBin() throws IOException {
    	int source = dis.readInt();
    	int destination = dis.readInt();
    	int weight = dis.readInt();
    	int type = dis.readInt();
    	int carryLength=dis.readInt();
    	
    	byte[] b = new byte[carryLength];
        dis.read(b);
    	String carryover = new String(b, Charset.forName("UTF-8"));
    	
        return new Edge(source, destination, weight, type, carryover);
    }

    protected Edge readEdgeString(String line) throws NoSuchElementException {
        String[] split = line.split(" ",5);

        if(split.length==5)
        {
            return new Edge(Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                    Integer.parseInt(split[3]),split[4]);
        }
        else if(split.length==4)
        {
            return new Edge(Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                    Integer.parseInt(split[3]));
        }
        else
        {
            throw new NoSuchElementException("Malformed edge:"+line);
        }
    }

	@Override
	public Edge nextEdge() throws NoGraphOpenException, NoSuchElementException {

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		if (edgesRead >= edges)
			throw new NoSuchElementException();

		if (bin) {
			try {
				edgesRead++;
                return readEdgeBin();
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
				edgesRead++;
                return readEdgeString(line);
			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}
	}

	public void close() {
		try {
			if(br!=null)
				br.close();
			else
				dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected boolean validGraphType(String type)
	{
		return true;
	}
	
	protected boolean validGraphRevision(String type, String revision)
	{
		return true;
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
		
        return meta;
	}

}
