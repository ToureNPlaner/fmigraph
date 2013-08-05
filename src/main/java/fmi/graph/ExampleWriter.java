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

import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.standard.Edge;
import fmi.graph.metaio.MetaData;
import fmi.graph.standard.Node;
import fmi.graph.standard.Writer;

import java.io.File;
import java.io.IOException;

public class ExampleWriter {

	
	public static void main(String[] args) {
		
		Writer w = new Writer();
		
		try {
			w.createBin(new File("test.bin"));
            //w.create(new File("test.txt"));
			w.setNodeCount(1000);
			w.setEdgeCount(2000);

            MetaData data = new MetaData();
            data.addComment("This is a totally awesome comment");
            data.addComment("and here comes another comment that tells you how great this format is!");
            data.addComment("embedded : isn't that unbelievable");
            data.add("Producer", "ExampleWriter");
            w.writeMetaData(data);
			
			for(int n=0;n<1000;n++)
			{
				w.writeNode(new Node(n, n, Math.random(), Math.random(), (int)(Math.random()*100), "Teststring 1"));
			}
			
			for(int m=0;m<2000;m++)
			{
				w.writeEdge(new Edge(m,m+1,(int)(Math.random()*100),(int)(Math.random()*100),"Teststring 2"));
			}
			w.close();
			
			
			
		} catch (IOException e){
			e.printStackTrace();
		} catch (InvalidFunctionException e){
            e.printStackTrace();
        }

	}

}
