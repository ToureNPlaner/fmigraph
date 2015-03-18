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
package fmi.graph.maxspeed;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class Writer extends fmi.graph.definition.Writer {

    public Writer(OutputStream out, boolean binary) {
        super(out, binary);
        this.type = "maxspeed";
        this.revision = 1;
    }

    public Writer(File out, boolean binary) throws IOException{
        super(out, binary);
        this.type = "maxspeed";
        this.revision = 1;
    }

}
