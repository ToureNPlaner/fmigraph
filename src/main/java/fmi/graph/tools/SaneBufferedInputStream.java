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