/*
 * Copyright 2016-2018 Leon Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moilioncircle.redis.replicator.cmd.parser;

import org.junit.Test;

import com.moilioncircle.redis.replicator.cmd.impl.FlushAllCommand;
import com.moilioncircle.redis.replicator.cmd.impl.FlushDBCommand;

/**
 * @author Leon Chen
 * @since 2.1.0
 */
public class FlushAllParserTest extends AbstractParserTest {
    @Test
    public void parse() {
        {
            FlushAllParser parser = new FlushAllParser();
            FlushAllCommand cmd = parser.parse(toObjectArray("flushall".split(" ")));
            assertEquals(false, cmd.isAsync());
            assertEquals(false, cmd.isSync());

            parser = new FlushAllParser();
            cmd = parser.parse(toObjectArray("flushall async".split(" ")));
            assertEquals(true, cmd.isAsync());
            assertEquals(false, cmd.isSync());
    
            parser = new FlushAllParser();
            cmd = parser.parse(toObjectArray("flushall sync".split(" ")));
            assertEquals(false, cmd.isAsync());
            assertEquals(true, cmd.isSync());
        }

        {
            FlushDBParser parser = new FlushDBParser();
            FlushDBCommand cmd = parser.parse(toObjectArray("flushdb".split(" ")));
            assertEquals(false, cmd.isAsync());
            assertEquals(false, cmd.isSync());

            parser = new FlushDBParser();
            cmd = parser.parse(toObjectArray("flushdb async".split(" ")));
            assertEquals(true, cmd.isAsync());
            assertEquals(false, cmd.isSync());
    
            parser = new FlushDBParser();
            cmd = parser.parse(toObjectArray("flushdb sync".split(" ")));
            assertEquals(false, cmd.isAsync());
            assertEquals(true, cmd.isSync());
        }
    }
}