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

import com.moilioncircle.redis.replicator.cmd.impl.DecrByCommand;
import com.moilioncircle.redis.replicator.cmd.impl.DecrCommand;
import com.moilioncircle.redis.replicator.cmd.impl.HIncrByCommand;
import com.moilioncircle.redis.replicator.cmd.impl.IncrByCommand;
import com.moilioncircle.redis.replicator.cmd.impl.IncrCommand;
import com.moilioncircle.redis.replicator.cmd.impl.ZIncrByCommand;

import junit.framework.TestCase;

/**
 * @author Leon Chen
 * @since 2.1.0
 */
public class DecrByParserTest extends AbstractParserTest {
    @Test
    public void parse() {
        {
            DecrByParser parser = new DecrByParser();
            DecrByCommand cmd = parser.parse(toObjectArray("decrby key 5".split(" ")));
            assertEquals("key", cmd.getKey());
            assertEquals(5, cmd.getValue());
        }

        {
            IncrByParser parser = new IncrByParser();
            IncrByCommand cmd = parser.parse(toObjectArray("incrby key 5".split(" ")));
            assertEquals("key", cmd.getKey());
        }

        {
            DecrParser parser = new DecrParser();
            DecrCommand cmd = parser.parse(toObjectArray("decr key".split(" ")));
            assertEquals("key", cmd.getKey());
        }

        {
            IncrParser parser = new IncrParser();
            IncrCommand cmd = parser.parse(toObjectArray("incr key".split(" ")));
            assertEquals("key", cmd.getKey());
        }

        {
            ZIncrByParser parser = new ZIncrByParser();
            ZIncrByCommand cmd = parser.parse(toObjectArray("zincrby key 5 mem".split(" ")));
            assertEquals("key", cmd.getKey());
            TestCase.assertEquals(5, cmd.getIncrement(), 0);
            assertEquals("mem", cmd.getMember());
        }

        {
            HIncrByParser parser = new HIncrByParser();
            HIncrByCommand cmd = parser.parse(toObjectArray("hincrby key mem 5".split(" ")));
            assertEquals("key", cmd.getKey());
            assertEquals(5, cmd.getIncrement());
            assertEquals("mem", cmd.getField());
        }
    }

}