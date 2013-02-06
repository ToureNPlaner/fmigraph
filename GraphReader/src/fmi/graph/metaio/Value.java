package fmi.graph.metaio;

import java.util.Date;

/**
 * @author Niklas Schnelle
 */
public class Value {
    public String[] comments;
    public String   value;

    public Value(String[] comments, String value){
        this.comments = comments;
        this.value = value;
    }

    public double asDouble(){
        return Double.parseDouble(value);
    }

    public long asLong(){
        return Long.parseLong(value, 10);
    }

    public Date asDate(){
        return new Date(asLong()/1000);
    }

}
