package fmi.graph.exceptions;

import fmi.graph.definition.GraphException;

public class CoherencyException extends GraphException{
	
	private static final long serialVersionUID = -1511156500831967164L;

	public CoherencyException()
	{
		super();
	}
	
	public CoherencyException(String s)
	{
		super(s);
	}

}
