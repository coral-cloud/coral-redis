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

package com.moilioncircle.redis.replicator;

import static com.moilioncircle.redis.replicator.Status.CONNECTED;
import static com.moilioncircle.redis.replicator.Status.DISCONNECTED;
import static com.moilioncircle.redis.replicator.Status.DISCONNECTING;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.moilioncircle.redis.replicator.io.RedisInputStream;
import com.moilioncircle.redis.replicator.rdb.RdbParser;

/**
 * @author Leon Chen
 * @since 2.1.0
 */
public class RedisRdbReplicator extends AbstractReplicator {
    
    public RedisRdbReplicator(File file, Configuration configuration) throws FileNotFoundException {
        this(new FileInputStream(file), configuration);
    }
    
    public RedisRdbReplicator(InputStream in, Configuration configuration) {
        Objects.requireNonNull(in);
        Objects.requireNonNull(configuration);
        this.configuration = configuration;
        this.inputStream = new RedisInputStream(in, this.configuration.getBufferSize());
        this.inputStream.setRawByteListeners(this.rawByteListeners);
        if (configuration.isUseDefaultExceptionListener())
            addExceptionListener(new DefaultExceptionListener());
    }
    
    @Override
    protected void doOpen() throws IOException {
        try {
            new RdbParser(inputStream, this).parse();
        } catch (EOFException ignore) {
        }
    }
    
    @Override
    protected void doClose() throws IOException {
        compareAndSet(CONNECTED, DISCONNECTING);
        try {
            if (inputStream != null) {
                this.inputStream.setRawByteListeners(null);
                inputStream.close();
            }
        } catch (IOException ignore) {
            /*NOP*/
        } finally {
            setStatus(DISCONNECTED);
        }
    }
}
