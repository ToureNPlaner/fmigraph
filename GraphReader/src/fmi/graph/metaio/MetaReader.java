package fmi.graph.metaio;

import fmi.graph.exceptions.BrokenMetaHeaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Niklas Schnelle
 */
public class MetaReader {

    protected static final Pattern compattern = Pattern.compile("^##(.+)$");
    protected static final Pattern kvpattern = Pattern.compile("^#\\s*(\\w+)\\s*:\\s*(.+)");

    private Matcher mkv;
    private Matcher mcom;

    public MetaReader(){
        mkv = kvpattern.matcher("");
        mcom = compattern.matcher("");
    }


    public MetaData readMetaData(BufferedReader r) throws IOException, BrokenMetaHeaderException {
        if (r == null)
            return null;
        MetaData meta = new MetaData();

        String line = r.readLine();
        while (line != null && !line.equals("#")){
            if (!matchLine(meta, line))
                throw new BrokenMetaHeaderException(line);
            line = r.readLine();
        }

        return meta;
    }

    private boolean matchLine(MetaData meta, String line) {
        if (mcom.reset(line).matches()){
            String comment = mcom.group(1).trim();
            meta.AddComment(comment);
        } else if (mkv.reset(line).matches()){
            String key = mkv.group(1);
            String value = mkv.group(2).trim();
            meta.Add(key, value);
        } else {
            return false;
        }
        return true;
    }

}