package fmi.graph.maxspeed;

import java.io.IOException;

import fmi.graph.exceptions.InvalidFunctionException;
import fmi.graph.metaio.MetaData;

public class Writer extends fmi.graph.standard.Writer {

	public Writer() {
		type = "maxspeed";
		revision = 1;
	}

	public void writeMetaData(MetaData data) throws IOException,
			InvalidFunctionException {

		data.add("highway", "yes");
		super.writeMetaData(data);

	}
}
