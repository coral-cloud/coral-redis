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

import com.moilioncircle.redis.replicator.cmd.impl.LInsertCommand;
import com.moilioncircle.redis.replicator.cmd.impl.LInsertType;

import junit.framework.TestCase;

/**
 * @author Leon Chen
 * @since 2.1.0
 */
public class LInsertParserTest extends AbstractParserTest {
    @Test
    public void parse() {
        LInsertParser parser = new LInsertParser();
        LInsertCommand cmd = parser.parse(toObjectArray("LINSERT mylist BEFORE World There".split(" ")));
        assertEquals("mylist", cmd.getKey());
        TestCase.assertEquals(LInsertType.BEFORE, cmd.getlInsertType());
        assertEquals("World", cmd.getPivot());
        assertEquals("There", cmd.getValue());

        cmd = parser.parse(toObjectArray("LINSERT mylist AFTER World There".split(" ")));
        assertEquals("mylist", cmd.getKey());
        TestCase.assertEquals(LInsertType.AFTER, cmd.getlInsertType());
        assertEquals("World", cmd.getPivot());
        assertEquals("There", cmd.getValue());
    }

}