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
package fmi.graph.chgraph;

import fmi.graph.definition.GraphException;
import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Reader extends fmi.graph.definition.Reader {

    public Reader(File in, boolean binary) throws IOException{
        super(in, binary);

    }

    public Reader(InputStream in, boolean binary) {
        super(in, binary);

    }

    @Override
    public Node nextNode() throws NoGraphOpenException, GraphException {
        return (Node) super.nextNode();
    }

    @Override
    public Edge nextEdge() throws NoGraphOpenException, GraphException {
        return (Edge) super.nextEdge();
    }

    @Override
    protected Node readNodeBin() throws IOException {
        return new Node(dis);
    }

    @Override
    protected Node readNodeString(String line) throws NoSuchElementException {
        return new Node(line);
    }

    @Override
    protected Edge readEdgeBin() throws IOException {
        return new Edge(dis);
    }

    @Override
    protected Edge readEdgeString(String line) throws NoSuchElementException {
        return new Edge(line);
    }

}
