package fmi.graph.maxspeed;

import java.io.IOException;


import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;

public class Reader extends fmi.graph.standard.Reader {

	
	public Node nextNode() throws NoGraphOpenException, NoSuchElementException {
		String line="";
		String[] split;
		
		if(br==null)
			throw new NoGraphOpenException();
		
		if(nodesRead >= nodes)
			throw new NoSuchElementException();
		
		try {
			while(true)
			{
				line = br.readLine().trim();
				if(line.charAt(0)!='#')
					break;
			}
			nodesRead++;
			split = line.split(" ");
			return new Node(Integer.parseInt(split[0]), Long.parseLong(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Integer.parseInt(split[4]));

		} catch (IOException e) {
			System.out.println(line);
			e.printStackTrace();
			throw new NoGraphOpenException();
		}
	}
	
	public Edge nextEdge() throws NoGraphOpenException, NoSuchElementException {
		String line;
		String[] split;
		
		if(br==null)
			throw new NoGraphOpenException();
		
		if(edgesRead >= edges)
			throw new NoSuchElementException();
		
		try {
			while(true)
			{
				line = br.readLine().trim();
				if(line.charAt(0)!='#')
					break;
			}
			edgesRead++;
			split = line.split(" ");
			return new Edge(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));

		} catch (IOException e) {
			e.printStackTrace();
			throw new NoGraphOpenException();
		}
	}

}
