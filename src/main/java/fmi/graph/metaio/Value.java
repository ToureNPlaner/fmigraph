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
