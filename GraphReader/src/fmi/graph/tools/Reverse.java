package fmi.graph.tools;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

import fmi.graph.reader.definition.Edge;
import fmi.graph.reader.definition.Node;
import fmi.graph.reader.definition.Reader;
import fmi.graph.reader.exceptions.NoGraphOpenException;
import fmi.graph.reader.exceptions.NoSuchElementException;

public class Reverse {

	TreeSet<Edge> ts;
	Reader r = new fmi.graph.reader.standard.Reader();

	public void reverseGraph(File input, File output) {
		ts = new TreeSet<Edge>(new ReverseEdgeComparator());
		Node n;
		Edge e;

		try {
			r.open(input);

			if (output.exists())
				output.delete();

			BufferedWriter bw = new BufferedWriter(new FileWriter(output));

			bw.write(Integer.toString(r.getNodeCount()));
			bw.newLine();
			bw.write(Integer.toString(r.getEdgeCount()));
			bw.newLine();
			while (r.hasNextNode()) {
				n = r.nextNode();
				bw.write(n.getId() + " " + n.getOsmId() + " " + n.getLat() + " " + n.getLon() + " " + n.getElevation());
				bw.newLine();
			}
			while (r.hasNextEdge()) {
				e = r.nextEdge();
				ts.add(e);
			}
			while (ts.size() > 0) {
				e = ts.first();
				ts.remove(e);
				bw.write(e.getTarget() + " " + e.getSource() + " " + e.getWeight() + " " + e.getType());
				bw.newLine();
			}
			bw.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NoGraphOpenException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchElementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	class ReverseEdgeComparator implements Comparator<Edge> {

		public int compare(Edge o1, Edge o2) {
			if (o1.getTarget() < o2.getTarget())
				return -1;
			if (o1.getTarget() > o2.getTarget())
				return 1;
			if (o1.getSource() < o2.getSource())
				return -1;
			if (o1.getSource() > o2.getSource())
				return 1;
			if (o1.getWeight() < o1.getWeight())
				return -1;
			if (o1.getWeight() > o2.getWeight())
				return 1;
			return 0;
		}
	}
	
	
	public static void main(String[] args)
	{
		Reverse r = new Reverse();
		r.reverseGraph(new File("D:/graph/stuttgart-regbez.txt"), new File("D:/graph/stuttgart-regbezREV.txt"));
	}

}
