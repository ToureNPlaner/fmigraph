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
package fmi.graph.metaio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.io.Writer;

/**
 * @author Niklas Schnelle
 */
public class MetaWriter {
    private static final Charset cset = Charset.forName("UTF-8");

    public void writeMetaDataRaw(OutputStream out, MetaData data) throws IOException {
        for (String comment : data.comments){
            out.write(("# " + comment + '\n').getBytes(cset));
        }
        for (Map.Entry<String, Value> entry : data.entrySet()) {
            for (String comment : entry.getValue().comments){
                out.write(("# " + comment + '\n').getBytes(cset));
            }
            out.write(("# " + entry.getKey() + " : " + entry.getValue().value + '\n').getBytes(cset));
        }
        out.write('\n');
    }

    public void writeMetaDataWriter(Writer out, MetaData data) throws IOException {
        for (String comment : data.comments) {
            out.write("# " + comment + '\n');
        }
        for (Map.Entry<String, Value> entry : data.entrySet()) {
            for (String comment : entry.getValue().comments) {
                out.write("# " + comment + '\n');
            }
            out.write("# " + entry.getKey() + " : " + entry.getValue().value + '\n');
        }
        out.write('\n');
    }
}
