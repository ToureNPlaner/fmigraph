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
		br = new BufferedReader(new FileReader(graph));
		readHead();
		bin = false;
	}

	@Override
	public void openBin(File graph) throws IOException {
		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(
				graph)));
		readHead();
		bin = true;
	}

	@Override
	public void read(InputStream in) throws IOException {
		br = new BufferedReader(new InputStreamReader(in));
		readHead();
		bin = false;
	}

	@Override
	public void readBin(InputStream in) throws IOException {
		dis = new DataInputStream(new BufferedInputStream(in));
		readHead();
		bin = true;
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
				split = line.split(" ");
				return new Node(Integer.parseInt(split[0]),
						Long.parseLong(split[1]), Double.parseDouble(split[2]),
						Double.parseDouble(split[3]),
						Integer.parseInt(split[4]));

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
				split = line.split(" ");
				return new Edge(Integer.parseInt(split[0]),
						Integer.parseInt(split[1]), Integer.parseInt(split[2]),
						Integer.parseInt(split[3]));

			} catch (IOException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}
	}

	public void close() {
		try {
			br.close();
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
