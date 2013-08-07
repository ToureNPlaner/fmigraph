package fmi.graph.chgraph;

import fmi.graph.exceptions.NoSuchElementException;

import java.io.IOException;

/**
 * Reader for Contraction Hierarchy enabled Graphs beware to cast the returned
 * Edge/Node objects to chgraph.Edge/Node to access the additional data
 * 
 * @author Niklas Schnelle
 */
public class Reader extends fmi.graph.standard.Reader {

	@Override
	protected Node readNodeBin() throws IOException {
		return new fmi.graph.chgraph.Node(dis.readInt(), dis.readLong(),
				dis.readDouble(), dis.readDouble(), dis.readInt(),
				dis.readInt());
	}

	@Override
	protected Node readNodeString(String line) throws NoSuchElementException {
		String[] split = line.split(" ", 7);
		if (split.length == 7) {
			return new fmi.graph.chgraph.Node(Integer.parseInt(split[0]),
					Long.parseLong(split[1]), Double.parseDouble(split[2]),
					Double.parseDouble(split[3]), Integer.parseInt(split[4]),
					Integer.parseInt(split[5]), split[6]);
		} else if (split.length == 6) {
			return new fmi.graph.chgraph.Node(Integer.parseInt(split[0]),
					Long.parseLong(split[1]), Double.parseDouble(split[2]),
					Double.parseDouble(split[3]), Integer.parseInt(split[4]),
					Integer.parseInt(split[5]));
		} else {
			throw new NoSuchElementException("Malformed node:" + line);
		}
	}

	@Override
	protected Edge readEdgeString(String line) throws NoSuchElementException {
		String[] split = line.split(" ", 8);

		if (split.length == 8) {
			return new fmi.graph.chgraph.Edge(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]), Integer.parseInt(split[2]),
					Integer.parseInt(split[3]), Integer.parseInt(split[4]),
					Integer.parseInt(split[5]), Integer.parseInt(split[6]),
					split[7]);
		} else if (split.length == 7) {
			return new fmi.graph.chgraph.Edge(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]), Integer.parseInt(split[2]),
					Integer.parseInt(split[3]), Integer.parseInt(split[4]),
					Integer.parseInt(split[5]), Integer.parseInt(split[6]));
		} else {
			throw new NoSuchElementException("Malformed edge:" + line);
		}
	}

	@Override
	protected Edge readEdgeBin() throws IOException {
		return new fmi.graph.chgraph.Edge(dis.readInt(), dis.readInt(),
				dis.readInt(), dis.readInt(), dis.readInt(), dis.readInt(),
				dis.readInt());
	}

}
