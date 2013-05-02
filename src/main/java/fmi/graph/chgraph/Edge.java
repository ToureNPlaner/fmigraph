package fmi.graph.chgraph;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Edges for Contraction Hierarchy graphs, basically just standard Edges
 * plus euclidian distance and ids of the skipped edges
 *
 * @author Niklas Schnelle
 */
public class Edge extends fmi.graph.standard.Edge {
    int euclidianDist;
    int skippedEdgeA;
    int skippedEdgeB;

    public Edge(int source, int target, int weight, int type, int euclidianDist, int skippedEdgeA, int skippedEdgeB) {
        super(source, target, weight, type);
        this.euclidianDist = euclidianDist;
        this.skippedEdgeA = skippedEdgeA;
        this.skippedEdgeB = skippedEdgeB;
    }

    public Edge(int source, int target, int weight, int type, int euclidianDist, int skippedEdgeA, int skippedEdgeB, String carryover) {
        super(source, target, weight, type, carryover);
        this.euclidianDist = euclidianDist;
        this.skippedEdgeA = skippedEdgeA;
        this.skippedEdgeB = skippedEdgeB;
    }

    public int getEuclidianDist()
    {
        return euclidianDist;
    }

    public int getSkippedEdgeA()
    {
        return skippedEdgeA;
    }

    public int getSkippedEdgeB()
    {
        return skippedEdgeB;
    }
    public String toBaseString()
    {
        return super.toBaseString()+" "+euclidianDist+" "+skippedEdgeA+" "+skippedEdgeB;
    }

    @Override
    public void writeBin(DataOutputStream dos) throws IOException {
        super.writeBin(dos);
        dos.writeInt(euclidianDist);
        dos.writeInt(skippedEdgeA);
        dos.writeInt(skippedEdgeB);
    }
}
