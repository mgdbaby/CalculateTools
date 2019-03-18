package com.amao.calculate.utils;

import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO流操作工具
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 18/6/29 14:44
 */
public class IoUtils {

    public static final int BUFF_SIZE = 1024;

    /**
     * 写入字符串
     */
    public static void writeString(OutputStream outStream, String data) throws IOException {
        byte[] bytes = StringUtils.utf8ToBytes(data);
        int size = (bytes == null ? 0 : bytes.length);
        writeInt(outStream, size);
        if(size > 0){
            outStream.write(bytes);
        }
    }

    /**
     * 读取字符串
     */
    public static String readString(InputStream inputStream) throws IOException {
        int len = readInt(inputStream);
        if(len <= 0){
            return null;
        }
        byte[] bytes = IOUtil.readBytes(inputStream, 0, len);
        return StringUtils.bytes2Utf8(bytes);
    }

    public static void writeInt(OutputStream outStream, int data) throws IOException {
        byte[] bytes = intToBytes(data);
        outStream.write(bytes);
    }

    public static int readInt(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[4];
        int ret = inputStream.read(bytes);
        if(ret < 4){
            throw new RuntimeException("Read int data fail.");
        }
        return bytesToInt(bytes);
    }

    public static void writeLong(OutputStream outStream, long data) throws IOException {
        byte[] bytes = long2Bytes(data);
        outStream.write(bytes);
    }

    public static long readLong(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[8];
        int ret = inputStream.read(bytes);
//        if(ret < 8){
//            throw new RuntimeException("Read long data fail.");
//        }

        return bytes2Long(bytes);
    }

    private static byte[] intToBytes(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (0xff & i);
        b[1] = (byte) ((0xff00 & i) >> 8);
        b[2] = (byte) ((0xff0000 & i) >> 16);
        b[3] = (byte) ((0xff000000 & i) >> 24);
        return b;
    }

    private static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xff;
        num |= ((bytes[1] << 8) & 0xff00);
        num |= ((bytes[2] << 16) & 0xff0000);
        num |= ((bytes[3] << 24) & 0xff000000);
        return num;
    }

    private static byte[] long2Bytes(long num) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) (num >>> (56 - (i * 8)));
        }
        return b;
    }

    private static long bytes2Long(byte[] b) {
        long temp;
        long res = 0;
        for (int i = 0; i < 8; i++) {
            res <<= 8;
            temp = b[i] & 0xff;
            res |= temp;
        }
        return res;
    }

    public static void close(Closeable closeable){
        IOUtil.closeQuietly(closeable);
    }

    public static boolean canReadStream(InputStream inputStream){
        try {
            return inputStream.available() > 0;
        } catch (IOException e) {
            LogUtil.e(e);
        }
        return false;
    }

}
