package com.yzf.study.io.nio.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @description: 测试Pipe
 * @author leo
 * @date 2023/9/5 15:20
 * @version 1.0
 */
public class TestPipe {

    public static void main(String[] args) throws Exception {
        // 1、获取通道
        Pipe pipe = Pipe.open();
        // 2、获取 sink 管道，用来传送数据
        Pipe.SinkChannel sinkChannel = pipe.sink();
        // 3、申请一定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello world".getBytes());
        byteBuffer.flip();
        // 4、sink 发送数据
        sinkChannel.write(byteBuffer);


        // 5、创建接收 pipe 数据的 source 管道
        Pipe.SourceChannel sourceChannel = pipe.source();
        // 6、接收数据，并保存到缓冲区中
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
        int length = sourceChannel.read(byteBuffer2);
        System.out.println(new String(byteBuffer2.array(), 0, length));
        sourceChannel.close();
        sinkChannel.close();

    }

}
