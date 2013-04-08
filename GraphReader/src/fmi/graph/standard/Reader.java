package fmi.graph.standard;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;

public class Reader implements fmi.graph.definition.Reader {

	protected boolean bin = false;

	protected int nodes;
	protected int edges;
	protected int nodesRead = 0;
	protected int edgesRead = 0;

	protected DataInputStream dis = null;
	protected BufferedReader br = null;

	@Override
	public void open(File graph) throws IOException {
		bin = false;
		br = new BufferedReader(new FileReader(graph));
		readHead();
	}

	@Override
	public void openBin(File graph) throws IOException {
		bin = true;
		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(
				graph)));
		readHead();
	}
	
	@Override
	public void openGZip(File graph) throws IOException {
		bin = true;
		dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(
				graph))));
		readHead();
	}

	@Override
	public void read(InputStream in) throws IOException {
		bin = false;
		br = new BufferedReader(new InputStreamReader(in));
		readHead();

	}

	@Override
	public void readBin(InputStream in) throws IOException {
		bin = true;
		dis = new DataInputStream(new BufferedInputStream(in));
		readHead();

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

	@Override
	public Node nextNode() throws NoGraphOpenException, NoSuchElementException {

		if (br == null && dis == null)
			throw new NoGraphOpenException();

		if (nodesRead >= nodes)
			throw new NoSuchElementException();

		if (bin) {
			nodesRead++;
			try {
				return new Node(dis.readInt(), dis.readLong(),
						dis.readDouble(), dis.readDouble(), dis.readInt());
			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		} else {
			String line = "";
			String[] split;
			try {
				while (true) {
					line = br.readLine().trim();
					if (line.charAt(0) != '#')
						break;
				}
				nodesRead++;
				split = line.split(" ",6);
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

			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
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
				return new Edge(dis.readInt(), dis.readInt(), dis.readInt(),
						dis.readInt());
			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		} else {
			String line;
			String[] split;

			try {
				while (true) {
					line = br.readLine().trim();
					if (line.charAt(0) != '#')
						break;
				}
				edgesRead++;
				split = line.split(" ",5);
				
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

	private void readHead() throws IOException {

		nodes = -1;
		nodesRead = 0;
		edges = -1;
		edgesRead = 0;

		if (bin) {
			nodes = dis.readInt();
			edges = dis.readInt();
		} else {
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

	}

}
