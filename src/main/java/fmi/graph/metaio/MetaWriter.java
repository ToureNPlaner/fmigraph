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
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

/**
 * @author Niklas Schnelle
 */
public class MetaWriter {
    private static final Charset cset = Charset.forName("UTF-8");


    /**
     * Converts the given byte[] to a hex string, leading zeros are NOT discarded
     * @param data
     * @return
     */
    private static String convertToHex(byte[] data) {
        final StringBuilder hexbuilder = new StringBuilder(data.length * 2);
        for (byte b : data) {
            hexbuilder.append(Integer.toHexString((b >>> 4) & 0x0F));
            hexbuilder.append(Integer.toHexString(b & 0x0F));
        }
        return hexbuilder.toString();
    }

    public void writeMetaDataRaw(OutputStream out, MetaData data) throws IOException {
        for (String comment : data.comments) {
            out.write(("# " + comment + '\n').getBytes(cset));
        }
        for (Map.Entry<String, Value> entry : data.entrySet()) {
            for (String comment : entry.getValue().comments) {
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
