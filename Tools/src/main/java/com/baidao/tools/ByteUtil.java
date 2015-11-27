package com.baidao.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by chengxin on 3/2/15.
 */
public class ByteUtil {
    private static final String TAG = ByteUtil.class.getSimpleName();

    static public byte[] intToString(int data){
        byte[] result = new byte[4];
        int byte1 = data % 0x100;
        int byte2 = (data - byte1) % 0x10000 / 0x100;
        int byte3 = (data - byte1 - byte2 * 0x100) % 0x1000000  / 0x10000;
        int byte4 = (data- byte1 - byte2* 0x100 - byte3*0x10000) / 0x1000000;
        result[0] = (byte)byte4;
        result[1] = (byte)byte3;
        result[2] = (byte)byte2;
        result[3] = (byte)byte1;
        return result;
    }

    static public byte[] retrieveData(Socket socket, byte[] requestBuffer){
        byte[] data = new byte[1024];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        try {
            OutputStream out = socket.getOutputStream();
            out.write(requestBuffer);
            out.flush();
            int length = 0;
            InputStream in = socket.getInputStream();
            int totalSize = 0;
            while ((length = in.read(data)) != -1) {
                if(outStream.size() == 0){
                    totalSize = data[2] + data[3] * 256 + 4;
                }
                outStream.write(data, 0, length);
                totalSize -= length;
                if(totalSize == 0){
                    break;
                }
            }
        } catch(SocketTimeoutException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }

    public static byte[] shortToBytes(short data) {
        byte[] b = new byte[2];
        b[0] = (byte) (data & 0xff);
        b[1] = (byte) (data>>8 & 0xff);
        return b;
    }

    public static byte[] intToBytes(int data) {
        byte[] b = new byte[4];
        b[0] = (byte) (data & 0xff);
        b[1] = (byte) (data>>8 & 0xff);
        b[2] = (byte) (data>>16 & 0xff);
        b[3] = (byte) (data>>24 & 0xff);
        return b;
    }

    public static byte[] longToBytes(long data) {
        byte[] b = new byte[8];
        b[0] = (byte) (data & 0xff);
        b[1] = (byte) (data>>8 & 0xff);
        b[2] = (byte) (data>>16 & 0xff);
        b[3] = (byte) (data>>24 & 0xff);
        b[4] = (byte) (data>>32 & 0xff);
        b[5] = (byte) (data>>40 & 0xff);
        b[6] = (byte) (data>>48 & 0xff);
        b[7] = (byte) (data>>56 & 0xff);
        return b;
    }

    public static int bytesToInt(byte[] bytes) {
        int value = 0;
        byte b = 0x00;
        int len = bytes.length;
        for (int i=0; i<4; i++) {
            if (i > (len - 1)) {
                value = value | ((b & 0xff)<<(i * 8));
            } else {
                value = value | ((bytes[i] & 0xff)<<(i * 8));
            }
        }
        return value;
    }

    public static long bytesToLong(byte[] bytes) {
        long value = 0;
        byte b = 0x00;
        int len = bytes.length;
        for (int i=0; i<8; i++) {
            if (i > (len - 1)) {
                value = value | ((b & 0xff)<<(i * 8));
            } else {
                value = value | ((bytes[i] & 0xff)<<(i * 8));
            }
        }
        return value;
    }

}
