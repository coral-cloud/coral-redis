package org.coral.redis.cluster.rdb;

import com.moilioncircle.redis.replicator.io.CRCOutputStream;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * RdbOutputStreams
 *
 */
public class RdbOutputStreams {
    
    public static void close(OutputStream out) {
        if (out == null) return;
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void closeQuietly(OutputStream out) {
        if (out == null) return;
        try {
            out.close();
        } catch (Throwable t) {
        }
    }
    
    public static void write(int b, OutputStream out) {
        if (out == null) return;
        try {
            out.write(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void writeQuietly(int b, OutputStream out) {
        if (out == null) return;
        try {
            out.write(b);
        } catch (Throwable t) {
        }
    }
    
    public static void write(byte[] b, OutputStream out) {
        if (out == null) return;
        try {
            out.write(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void writeQuietly(byte b[], OutputStream out) {
        if (out == null) return;
        try {
            out.write(b);
        } catch (Throwable t) {
        }
    }
    
    public static void write(byte[] b, int off, int len, OutputStream out) {
        if (out == null) return;
        try {
            out.write(b, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void writeQuietly(byte[] b, int off, int len, OutputStream out) {
        if (out == null) return;
        try {
            out.write(b, off, len);
        } catch (Throwable t) {
        }
    }
    
    public static void flushQuietly(OutputStream out) {
        if (out == null) return;
        try {
            out.flush();
        } catch (Throwable t) {
        }
    }
    
    public static void flush(OutputStream out) {
        if (out == null) return;
        try {
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T call(Callable<T> callable) {
        if (callable == null) return null;
        try {
            return callable.call();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T callQuietly(Callable<T> callable) {
        if (callable == null) return null;
        try {
            return callable.call();
        } catch (Throwable txt) {
            return null;
        }
    }
    
    public static BufferedOutputStream newBufferedOutputStream(String file, int buf) {
        return call(() -> newBufferedOutputStream(new File(file), buf));
    }
    
    public static BufferedOutputStream newBufferedOutputStream(File file, int buf) {
        return call(() -> new BufferedOutputStream(new FileOutputStream(file), buf));
    }
    
    public static CRCOutputStream newCRCOutputStream(String file, int buf) {
        return call(() -> newCRCOutputStream(new File(file), buf));
    }
    
    public static CRCOutputStream newCRCOutputStream(File file, int buf) {
        return call(() -> new CRCOutputStream(new BufferedOutputStream(new FileOutputStream(file), buf)));
    }
}
