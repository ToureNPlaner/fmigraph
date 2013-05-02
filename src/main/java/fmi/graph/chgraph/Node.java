package fmi.graph.chgraph;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Nodes for Contraction Hierarchy graphs, basically just standard Node
 * with an additional Level field
 *
 * @author Niklas Schnelle
 */
public class Node extends fmi.graph.standard.Node {
    int level;

    public Node(int id, long osm, double lat, double lon, int elevation, int level) {
        super(id, osm, lat, lon, elevation);
        this.level = level;
    }

    public Node(int id, long osm, double lat, double lon, int elevation, int level, String carryover){
        super(id, osm, lat, lon, elevation, carryover);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toBaseString() {
        return super.toBaseString() + " " + level;
    }

    @Override
    public void writeBin(DataOutputStream dos) throws IOException {
        super.writeBin(dos);
        dos.writeInt(level);
    }
}
