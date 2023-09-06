package com.yzf.study.io.nio.buffer;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author leo
 * @version 1.0
 * @description: ByteBuffer 的基本使用
 * @date 2023/9/5 14:25
 */
public class TestBuffer {

    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("d:\\test\\a.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        int capacity = 30;
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        byte[] bufArray = new byte[capacity];
        String dataStr = null;
        int readIndex = -1;

        while (true){
            // 将管道中的数据写入缓冲区
            readIndex = inChannel.read(buf);
            if (readIndex == -1){
                break;
            }
            // 切换 read 模式
            buf.flip();
            buf.get(bufArray, 0, readIndex);
            dataStr = new String(bufArray, 0, readIndex);
            System.out.print(dataStr);
            // 清空缓冲区
            buf.clear();

        }

        aFile.close();
    }


}



