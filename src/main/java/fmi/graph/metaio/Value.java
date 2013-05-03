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

import java.util.Date;

/**
 * Stores the value of a Key, Value tuple in MetaData
 *
 * @author Niklas Schnelle
 */
public class Value {
    public String[] comments;
    public String   value;

    /**
     * Creates a Value containing the value of the given long encoded as string
     * @param value
     */
    public Value(long value){
        this.comments = new String[0];
        this.value = Long.toString(value);
    }

    /**
     * Creates a Value containing the given date, stored as a string
     * of the unix time
     * @param date
     */
    public Value(Date date){
        this.comments = new String[0];
        this.value = Long.toString(date.getTime()/1000);
    }

    /**
     * Creates a Value containing the given value as a double, stored as a string
     * @param value
     */
    public Value(double value){
        this.comments = new String[0];
        this.value = Double.toString(value);
    }

    /**
     * Creates a Value for the given string
     * @param value
     */
    public Value(String value){
        this.comments = new String[0];
        this.value = value;
    }

    /**
     * Creates a value with the given string and commments.
     * This constructor is primarily used when reading the MetaData
     * in most other cases it is easiest to use the other constructors
     * @param comments
     * @param value
     */
    public Value(String[] comments, String value){
        this.comments = comments;
        this.value = value;
    }

    /**
     * Returns the Value decoded into a double
     * @return
     */
    public double asDouble(){
        return Double.parseDouble(value);
    }

    /**
     * Decodes the Value decoded into a long
     * @return
     */
    public long asLong(){
        return Long.parseLong(value, 10);
    }

    /**
     * Decodes the Value as a date, it's assumed to be
     * encoded as number string of the unix time (in seconds)
     * @return
     */
    public Date asDate(){
        return new Date(asLong()*1000);
    }

    /**
     * Returns the String representation of this value, omits the
     * associated comments
     * @return
     */
    public String toString(){
        return value;
    }

}
