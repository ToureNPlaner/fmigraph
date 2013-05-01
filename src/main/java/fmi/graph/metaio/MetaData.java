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

import java.util.*;

/**
 * @author Niklas Schnelle *
 */
public class MetaData {
    protected Map<String, Value> data;

    protected List<String> comments;

    public MetaData(){
        data = new TreeMap<String, Value>();
        comments = new ArrayList<String>();
    }

    public MetaData(MetaData orig){
        this.data = new HashMap<String, Value>(orig.data);
        this.comments = new ArrayList<String>(orig.comments);
    }

    /**
     * add a comment to this MetaData object without immediately associating a
     * key, value pair with it. Comments added without an associated key will be
     * accumulated and added to the next key that is added without a comment see add(String, String).
     * Thus if comments are read from a file they will be added to the next
     * key, value pair.
     * @param comment
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * Adds a key, value pair where the value is given as a string and will be
     * parsed by the {@link Value} class. Since no comments are explicitly associated with
     * this key, value pair it will be associated with all comments currently accumulated by
     * addComment(String).
     * Thus comments in a file will always be associated with the next key, value pair
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        data.put(key, new Value(comments.toArray(new String[0]), value));
        comments.clear();
    }

    /**
     * Adds the given Value with the provided key. The Value will not be modified thus
     * comments are kept. When writing out a MetaData object they will be printed before their associated
     * value so that they will be added to it when reading the file back in.
     * @param key
     * @param value
     */
    public void add(String key, Value value){
        data.put(key, value);
    }

    /**
     * Gets the Value associated with the given key, null if no
     * value is associated.
     *
     * @param key
     * @return
     */
    public Value get(String key){
        return data.get(key);
    }

    /**
     * Gets a set of all Key, Value Map.Entry<String, Value> elements associated with this MetaData object.
     * This should primarily be used for accessing all elements in bulk. Modifying elements via this Set is
     * discouraged.
     * @return
     */
    public Set<Map.Entry<String,Value>> entrySet() {
        return data.entrySet();
    }
}
