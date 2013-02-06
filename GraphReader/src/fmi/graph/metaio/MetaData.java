package fmi.graph.metaio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Niklas Schnelle *
 */
public class MetaData {
    public Map<String, Value> data;

    public List<String> comments;

    public MetaData(){
        data = new TreeMap<String, Value>();
        comments = new ArrayList<String>();
    }

    public void AddComment(String comment) {
        comments.add(comment);
    }

    public void Add(String key, String value) {
        data.put(key, new Value(comments.toArray(new String[0]), value));
        comments.clear();
    }

}
