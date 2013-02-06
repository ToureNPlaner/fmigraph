package fmi.graph.standard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fmi.graph.exceptions.NoGraphOpenException;
import fmi.graph.exceptions.NoSuchElementException;

public class Reader implements fmi.graph.definition.Reader{

	protected int nodes;
	protected int edges;
	protected int nodesRead=0;
	protected int edgesRead=0;
	
	protected BufferedReader br=null;

	@Override
	public void open(File graph) throws IOException {
		br = new BufferedReader(new FileReader(graph));
		readHead();		
	}
	
	@Override
	public void read(InputStream in) throws IOException {
		br = new BufferedReader(new InputStreamReader(in));
		readHead();
	}

	@Override
	public int getNodeCount() throws NoGraphOpenException {
		
		if(br==null)
			throw new NoGraphOpenException();
		
		return nodes;
	}

	@Override
	public int getEdgeCount() throws NoGraphOpenException {
		
		if(br==null)
			throw new NoGraphOpenException();
		
		return edges;
	}

	@Override
	public boolean hasNextNode() {
		if(br==null)
			return false;
		if(nodesRead<nodes)
			return true;
		
		return false;
	}

	@Override
	public boolean hasNextEdge() {
		if(br==null)
			return false;
		if(nodesRead==nodes&&edgesRead<edges)
			return true;
		return false;
			
	}

	@Override
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
			return new fmi.graph.standard.Node(Integer.parseInt(split[0]), Long.parseLong(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Integer.parseInt(split[4]));

		} catch (IOException e) {
			System.out.println(line);
			e.printStackTrace();
			throw new NoGraphOpenException();
		}
	}

	@Override
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
			return new fmi.graph.standard.Edge(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));

		} catch (IOException e) {
			e.printStackTrace();
			throw new NoGraphOpenException();
		}
	}
	
	public void close()
	{
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readHead() throws IOException
	{
		String line;
		
		nodes = -1;
		nodesRead = 0;
		edges = -1;
		edgesRead = 0;
		
		while(true)
		{
			line = br.readLine().trim();
			if(line.charAt(0)=='#')
				continue;
			if(nodes == -1)
				nodes = Integer.parseInt(line);
			else if(edges == -1)
			{
				edges = Integer.parseInt(line);
				break;
			}
			
			
			
		}
		
	}
	
	
	
	

}
