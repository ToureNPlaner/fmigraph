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
package fmi.graph;

import fmi.graph.definition.GraphException;
import fmi.graph.metaio.MetaData;
import fmi.graph.metaio.Value;
import fmi.graph.chgraph.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ExampleReader {
    public static void main(String[] args) {
        Node n;
        Edge e;
        int nodes = 0;
        int edges = 0;
        long start;
        try {
            start = System.currentTimeMillis();
            Reader r = new Reader(new File("test.bin"), true);
            MetaData meta = r.readMetaData();
            // MetaData meta = r.open(new File("test.txt"));

            System.out.println("Created at " + meta.get("Timestamp").asDate());
            System.out.println("by " + meta.get("Producer"));

            for (Map.Entry<String, Value> entry : meta.entrySet()) {
                System.out.println("Key: \"" + entry.getKey() + "\" with value: \"" + entry.getValue().value + '"');
                System.out.println("Comments:");
                for (String comment : entry.getValue().comments) {
                    System.out.println("\t" + comment);
                }
                System.out.println("---------------------------------------------------------------");
            }
            while (r.hasNextNode()) {
                n = r.nextNode();
                System.out.println(n);
                nodes++;
            }
            System.out.println("Nodes gelesen: " + nodes + " Nodes vorhanden: " + r.getNodeCount());
            while (r.hasNextEdge()) {
                e = r.nextEdge();
                System.out.println(e);
                edges++;
            }
            System.out.println("Edges gelesen: " + edges + " Edges vorhanden: " + r.getEdgeCount());
            r.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (GraphException ex) {
            ex.printStackTrace();
        }

    }

}
