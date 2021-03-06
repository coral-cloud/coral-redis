/*
 * Copyright 2016-2017 Leon Chen
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

package com.moilioncircle.redis.replicator.rdb.dump;

import static com.moilioncircle.redis.replicator.Constants.MODULE_SET;
import static com.moilioncircle.redis.replicator.Constants.RDB_LOAD_NONE;
import static com.moilioncircle.redis.replicator.Constants.RDB_MODULE_OPCODE_EOF;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_HASH;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_HASH_ZIPLIST;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_HASH_ZIPMAP;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_LIST;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_LIST_QUICKLIST;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_LIST_ZIPLIST;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_MODULE;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_MODULE_2;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_SET;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_SET_INTSET;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_STREAM_LISTPACKS;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_STRING;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_ZSET;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_ZSET_2;
import static com.moilioncircle.redis.replicator.Constants.RDB_TYPE_ZSET_ZIPLIST;
import static com.moilioncircle.redis.replicator.util.CRC64.crc64;
import static com.moilioncircle.redis.replicator.util.CRC64.longToByteArray;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.NoSuchElementException;

import com.moilioncircle.redis.replicator.Replicator;
import com.moilioncircle.redis.replicator.io.ByteBufferOutputStream;
import com.moilioncircle.redis.replicator.io.RawByteListener;
import com.moilioncircle.redis.replicator.io.RedisInputStream;
import com.moilioncircle.redis.replicator.rdb.BaseRdbEncoder;
import com.moilioncircle.redis.replicator.rdb.BaseRdbParser;
import com.moilioncircle.redis.replicator.rdb.DefaultRdbValueVisitor;
import com.moilioncircle.redis.replicator.rdb.datatype.Module;
import com.moilioncircle.redis.replicator.rdb.module.ModuleParser;
import com.moilioncircle.redis.replicator.rdb.skip.SkipRdbParser;
import com.moilioncircle.redis.replicator.util.ByteArray;
import com.moilioncircle.redis.replicator.util.ByteBuilder;

/**
 * @author Leon Chen
 * @since 3.1.0
 */
@SuppressWarnings("unchecked")
public class DumpRdbValueVisitor extends DefaultRdbValueVisitor {

    private class DefaultRawByteListener implements RawByteListener {
        private final int version;
        private final ByteBuilder builder;

        private DefaultRawByteListener(byte type, int version) {
            this.builder = ByteBuilder.allocate(DumpRdbValueVisitor.this.size);
            this.builder.put(type);
            int ver = DumpRdbValueVisitor.this.version;
            this.version = ver == -1 ? version : ver;
        }

        @Override
        public void handle(byte... rawBytes) {
            this.builder.put(rawBytes);
        }
    
        public void handle(ByteBuffer buffer) {
            this.builder.put(buffer);
        }

        public byte[] getBytes() {
            this.builder.put((byte) version);
            this.builder.put((byte) 0x00);
            List<ByteBuffer> buffers = this.builder.buffers();
            byte[] crc = longToByteArray(crc64(buffers));
            for (byte b : crc) {
                this.builder.put(b);
            }
            return this.builder.array();
        }
    }

    private final int size;
    private final int version;

    public DumpRdbValueVisitor(Replicator replicator) {
        this(replicator, -1);
    }

    public DumpRdbValueVisitor(Replicator replicator, int version) {
        this(replicator, version, 8192);
    }

    public DumpRdbValueVisitor(Replicator replicator, int version, int size) {
        super(replicator);
        this.version = version;
        this.size = size;
    }

    @Override
    public <T> T applyString(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_STRING, version);
        replicator.addRawByteListener(listener);
        try {
            new SkipRdbParser(in).rdbLoadEncodedStringObject();
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyList(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_LIST, version);
        replicator.addRawByteListener(listener);
        try {
            SkipRdbParser skipParser = new SkipRdbParser(in);
            long len = skipParser.rdbLoadLen().len;
            while (len > 0) {
                skipParser.rdbLoadEncodedStringObject();
                len--;
            }
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applySet(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_SET, version);
        replicator.addRawByteListener(listener);
        try {
            SkipRdbParser skipParser = new SkipRdbParser(in);
            long len = skipParser.rdbLoadLen().len;
            while (len > 0) {
                skipParser.rdbLoadEncodedStringObject();
                len--;
            }
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyZSet(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_ZSET, version);
        replicator.addRawByteListener(listener);
        try {
            SkipRdbParser skipParser = new SkipRdbParser(in);
            long len = skipParser.rdbLoadLen().len;
            while (len > 0) {
                skipParser.rdbLoadEncodedStringObject();
                skipParser.rdbLoadDoubleValue();
                len--;
            }
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyZSet2(RedisInputStream in, int version) throws IOException {
        if (this.version != -1 && this.version < 8 /* since redis rdb version 8 */) {
            // downgrade to RDB_TYPE_ZSET
            BaseRdbParser parser = new BaseRdbParser(in);
            BaseRdbEncoder encoder = new BaseRdbEncoder();
            DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_ZSET, version);
            try (ByteBufferOutputStream out = new ByteBufferOutputStream(size)) {
                long len = parser.rdbLoadLen().len;
                listener.handle(encoder.rdbSaveLen(len));
                while (len > 0) {
                    ByteArray element = parser.rdbLoadEncodedStringObject();
                    encoder.rdbGenericSaveStringObject(element, out);
                    double score = parser.rdbLoadBinaryDoubleValue();
                    encoder.rdbSaveDoubleValue(score, out);
                    len--;
                }
                listener.handle(out.toByteBuffer());
                return (T) listener.getBytes();
            }
        } else {
            DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_ZSET_2, version);
            replicator.addRawByteListener(listener);
            try {
                SkipRdbParser skipParser = new SkipRdbParser(in);
                long len = skipParser.rdbLoadLen().len;
                while (len > 0) {
                    skipParser.rdbLoadEncodedStringObject();
                    skipParser.rdbLoadBinaryDoubleValue();
                    len--;
                }
            } finally {
                replicator.removeRawByteListener(listener);
            }
            return (T) listener.getBytes();
        }
    }

    @Override
    public <T> T applyHash(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_HASH, version);
        replicator.addRawByteListener(listener);
        try {
            SkipRdbParser skipParser = new SkipRdbParser(in);
            long len = skipParser.rdbLoadLen().len;
            while (len > 0) {
                skipParser.rdbLoadEncodedStringObject();
                skipParser.rdbLoadEncodedStringObject();
                len--;
            }
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyHashZipMap(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_HASH_ZIPMAP, version);
        replicator.addRawByteListener(listener);
        try {
            new SkipRdbParser(in).rdbLoadPlainStringObject();
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyListZipList(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_LIST_ZIPLIST, version);
        replicator.addRawByteListener(listener);
        try {
            new SkipRdbParser(in).rdbLoadPlainStringObject();
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applySetIntSet(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_SET_INTSET, version);
        replicator.addRawByteListener(listener);
        try {
            new SkipRdbParser(in).rdbLoadPlainStringObject();
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyZSetZipList(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_ZSET_ZIPLIST, version);
        replicator.addRawByteListener(listener);
        try {
            new SkipRdbParser(in).rdbLoadPlainStringObject();
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyHashZipList(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_HASH_ZIPLIST, version);
        replicator.addRawByteListener(listener);
        try {
            new SkipRdbParser(in).rdbLoadPlainStringObject();
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyListQuickList(RedisInputStream in, int version) throws IOException {
        if (this.version != -1 && this.version < 7 /* since redis rdb version 7 */) {
            // downgrade to RDB_TYPE_LIST
            BaseRdbParser parser = new BaseRdbParser(in);
            BaseRdbEncoder encoder = new BaseRdbEncoder();
            try (ByteBufferOutputStream out = new ByteBufferOutputStream(size)) {
                int total = 0;
                long len = parser.rdbLoadLen().len;
                for (long i = 0; i < len; i++) {
                    RedisInputStream stream = new RedisInputStream(parser.rdbGenericLoadStringObject(RDB_LOAD_NONE));
            
                    BaseRdbParser.LenHelper.zlbytes(stream); // zlbytes
                    BaseRdbParser.LenHelper.zltail(stream); // zltail
                    int zllen = BaseRdbParser.LenHelper.zllen(stream);
                    for (int j = 0; j < zllen; j++) {
                        byte[] e = BaseRdbParser.StringHelper.zipListEntry(stream);
                        encoder.rdbGenericSaveStringObject(new ByteArray(e), out);
                        total++;
                    }
                    int zlend = BaseRdbParser.LenHelper.zlend(stream);
                    if (zlend != 255) {
                        throw new AssertionError("zlend expect 255 but " + zlend);
                    }
                }
        
                DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_LIST, version);
                listener.handle(encoder.rdbSaveLen(total));
                listener.handle(out.toByteBuffer());
                return (T) listener.getBytes();
            }
        } else {
            DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_LIST_QUICKLIST, version);
            replicator.addRawByteListener(listener);
            try {
                SkipRdbParser skipParser = new SkipRdbParser(in);
                long len = skipParser.rdbLoadLen().len;
                for (long i = 0; i < len; i++) {
                    skipParser.rdbGenericLoadStringObject();
                }
            } finally {
                replicator.removeRawByteListener(listener);
            }
            return (T) listener.getBytes();
        }
    }

    @Override
    public <T> T applyModule(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_MODULE, version);
        replicator.addRawByteListener(listener);
        try {
            SkipRdbParser skipParser = new SkipRdbParser(in);
            char[] c = new char[9];
            long moduleid = skipParser.rdbLoadLen().len;
            for (int i = 0; i < c.length; i++) {
                c[i] = MODULE_SET[(int) (moduleid >>> (10 + (c.length - 1 - i) * 6) & 63)];
            }
            String moduleName = new String(c);
            int moduleVersion = (int) (moduleid & 1023);
            ModuleParser<? extends Module> moduleParser = lookupModuleParser(moduleName, moduleVersion);
            if (moduleParser == null) {
                throw new NoSuchElementException("module parser[" + moduleName + ", " + moduleVersion + "] not register. rdb type: [RDB_TYPE_MODULE]");
            }
            moduleParser.parse(in, 1);
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    public <T> T applyModule2(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_MODULE_2, version);
        replicator.addRawByteListener(listener);
        try {
            BaseRdbParser parser = new BaseRdbParser(in);
            SkipRdbParser skipParser = new SkipRdbParser(in);
            char[] c = new char[9];
            long moduleid = skipParser.rdbLoadLen().len;
            for (int i = 0; i < c.length; i++) {
                c[i] = MODULE_SET[(int) (moduleid >>> (10 + (c.length - 1 - i) * 6) & 63)];
            }
            String moduleName = new String(c);
            int moduleVersion = (int) (moduleid & 1023);
            ModuleParser<? extends Module> moduleParser = lookupModuleParser(moduleName, moduleVersion);
            if (moduleParser == null) {
                SkipRdbParser skipRdbParser = new SkipRdbParser(in);
                skipRdbParser.rdbLoadCheckModuleValue();
            } else {
                moduleParser.parse(in, 2);
                long eof = parser.rdbLoadLen().len;
                if (eof != RDB_MODULE_OPCODE_EOF) {
                    throw new UnsupportedOperationException("The RDB file contains module data for the module '" + moduleName + "' that is not terminated by the proper module value EOF marker");
                }
            }
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }

    @Override
    @SuppressWarnings("resource")
    public <T> T applyStreamListPacks(RedisInputStream in, int version) throws IOException {
        DefaultRawByteListener listener = new DefaultRawByteListener((byte) RDB_TYPE_STREAM_LISTPACKS, version);
        replicator.addRawByteListener(listener);
        try {
            SkipRdbParser skipParser = new SkipRdbParser(in);
            long listPacks = skipParser.rdbLoadLen().len;
            while (listPacks-- > 0) {
                skipParser.rdbLoadPlainStringObject();
                skipParser.rdbLoadPlainStringObject();
            }
            skipParser.rdbLoadLen();
            skipParser.rdbLoadLen();
            skipParser.rdbLoadLen();
            long groupCount = skipParser.rdbLoadLen().len;
            while (groupCount-- > 0) {
                skipParser.rdbLoadPlainStringObject();
                skipParser.rdbLoadLen();
                skipParser.rdbLoadLen();
                long groupPel = skipParser.rdbLoadLen().len;
                while (groupPel-- > 0) {
                    in.skip(16);
                    skipParser.rdbLoadMillisecondTime();
                    skipParser.rdbLoadLen();
                }
                long consumerCount = skipParser.rdbLoadLen().len;
                while (consumerCount-- > 0) {
                    skipParser.rdbLoadPlainStringObject();
                    skipParser.rdbLoadMillisecondTime();
                    long consumerPel = skipParser.rdbLoadLen().len;
                    while (consumerPel-- > 0) {
                        in.skip(16);
                    }
                }
            }
        } finally {
            replicator.removeRawByteListener(listener);
        }
        return (T) listener.getBytes();
    }
}
