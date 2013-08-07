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
package fmi.graph.maxspeed;

import fmi.graph.exceptions.NoSuchElementException;

import java.io.IOException;

public class Reader extends fmi.graph.standard.Reader {

	@Override
	protected Edge readEdgeString(String line) throws NoSuchElementException {
		String[] split = line.split(" ", 6);

		if (split.length == 6) {
			return new fmi.graph.maxspeed.Edge(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]), Integer.parseInt(split[2]),
					Integer.parseInt(split[3]), Integer.parseInt(split[4]),
					split[5]);
		} else if (split.length == 5) {
			return new fmi.graph.maxspeed.Edge(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]), Integer.parseInt(split[2]),
					Integer.parseInt(split[3]), Integer.parseInt(split[4]));
		} else {
			throw new NoSuchElementException("Malformed edge:" + line);
		}
	}

	@Override
	protected Edge readEdgeBin() throws IOException {
		return new fmi.graph.maxspeed.Edge(dis.readInt(), dis.readInt(),
				dis.readInt(), dis.readInt(), dis.readInt());
	}

}
