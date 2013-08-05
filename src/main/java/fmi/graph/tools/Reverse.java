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
package fmi.graph.tools;

import fmi.graph.definition.Edge;
import fmi.graph.definition.GraphException;
import fmi.graph.definition.Node;
import fmi.graph.definition.Reader;
import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

public class Reverse {

	TreeSet<Edge> ts;
	Reader r = new fmi.graph.standard.Reader();

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
		} catch (GraphException e1) {
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
