package fmi.graph.exceptions;

import fmi.graph.definition.GraphException;

public class StartIdException extends GraphException{
	
	private static final long serialVersionUID = 5126216455688507208L;

	public StartIdException()
	{
		super();
	}

	public StartIdException(String s)
	{
		super(s);
	}
}

