package fmi.graph.exceptions;

import fmi.graph.definition.GraphException;

public class RevisionException extends GraphException{

	private static final long serialVersionUID = -2646393287050572891L;
	
	public RevisionException()
	{
		super();
	}
	public RevisionException(String s)
	{
		super(s);
	}
}
