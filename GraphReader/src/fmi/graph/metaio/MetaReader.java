package fmi.graph.metaio;

import fmi.graph.tools.SaneBufferedInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Niklas Schnelle
 */
public class MetaReader {
    protected static final int readaheadlimit = 4096;
    protected static final Pattern compattern = Pattern.compile("^#(.+)$");
    protected static final Pattern kvpattern = Pattern.compile("^#\\s*(\\w+)\\s*:\\s*(.+)");

    private Matcher mkv;
    private Matcher mcom;

    public MetaReader(){
        mkv = kvpattern.matcher("");
        mcom = compattern.matcher("");
    }

    public MetaData readMetaData(BufferedReader r) throws IOException {
        if (r == null)
            return null;
        MetaData meta = new MetaData();
        r.mark(readaheadlimit);
        String line = r.readLine();
        while (line != null && !line.equals("")){
            if (!matchLine(meta, line)){
                r.reset();
                break;
            }
            r.mark(readaheadlimit);
            line = r.readLine();
        }
        return meta;
    }



    public MetaData readMetaData(SaneBufferedInputStream r) throws IOException {
        if (r == null) return null;
        MetaData meta = new MetaData();
        r.mark(readaheadlimit);
        String line = r.readSaneLine();
        while (line != null && !line.equals("")) {
            if (!matchLine(meta, line)) {
                r.reset();
                break;
            }
            r.mark(readaheadlimit);
            line = r.readSaneLine();
        }
        return meta;
    }

    private boolean matchLine(MetaData meta, String line) {
        if (mkv.reset(line).matches()) {
            String key = mkv.group(1);
            String value = mkv.group(2).trim();
            meta.add(key, value);
        } else if (mcom.reset(line).matches()){
            String comment = mcom.group(1).trim();
            meta.addComment(comment);
        } else  {
            return false;
        }
        return true;
    }

}