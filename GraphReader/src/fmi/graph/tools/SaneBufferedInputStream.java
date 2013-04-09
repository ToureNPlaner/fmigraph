package fmi.graph.tools;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Niklas Schnelle
 */
public class SaneBufferedInputStream extends BufferedInputStream {
    private ByteArrayOutputStream bo;

    public SaneBufferedInputStream(InputStream in) {
        super(in);
        bo = new ByteArrayOutputStream();
    }

    /*
    Reads a line of input from a sanely encoded source,
    everything but UTF-8 + '\n' line endings is considered insane
    the trailing '\n' is not returned
     */
    public String readSaneLine() throws IOException {
        bo.reset();
        int b = 0;
        while (true) {
            b = this.read();
            if (b < 0 || b == '\n') break;
            bo.write(b);
        }
        return bo.toString("UTF-8");
    }
}