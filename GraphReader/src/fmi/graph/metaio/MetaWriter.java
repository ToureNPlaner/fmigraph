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
        for (Map.Entry<String, Value> entry : data.data.entrySet()) {
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
        for (Map.Entry<String, Value> entry : data.data.entrySet()) {
            for (String comment : entry.getValue().comments) {
                out.write("# " + comment + '\n');
            }
            out.write("# " + entry.getKey() + " : " + entry.getValue().value + '\n');
        }
        out.write('\n');
    }
}
