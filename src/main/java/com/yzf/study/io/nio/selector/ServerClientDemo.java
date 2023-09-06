package com.yzf.study.io.nio.selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @description: 测试 buffer 和 selector 的使用
 * @author leo
 * @date 2023/9/5 14:56
 * @version 1.0
 */
public class ServerClientDemo {

    /*
     t1     t2
     连接    建立管道，对写操作感兴趣
     [1]写出    [2]读取数据，设置管道对写操作感兴趣
     [3][5]读取    [4]写出
     */


    @Test
    public void ServerDemo() throws Exception{
        try {
            // ServerSocketChannel 可以监听新进来的 TCP 连接
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8000));
            ssc.configureBlocking(false);

            // 定义选择器
            Selector selector = Selector.open();

            // 在选择器中注册 channel，并且指定感兴趣的事件是 Accept
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            // 定义 读缓冲区
            ByteBuffer readBuff = ByteBuffer.allocate(1024);
            int readIndex = -1;

            // 定义 写缓冲区
            ByteBuffer writeBuff = ByteBuffer.allocate(128);


            while (true) {
                // 阻塞到至少有一个通道在你注册的事件上就绪了。
                int nReady = selector.select();
                // 获取就绪的通道
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                // 轮询就绪的通道
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        // 事件：接收到连接
                        // 创建新的连接，并且把连接注册到 selector 上，而且，
                        SocketChannel socketChannel = ssc.accept();
                        socketChannel.configureBlocking(false);
                        // 声明这个 channel 只对读操作感兴趣。
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        // 事件：可读
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuff.clear();
                        readIndex = socketChannel.read(readBuff);
                        readBuff.flip();
                        System.out.println("received : " + new String(readBuff.array(), 0, readIndex));
                        key.interestOps(SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        Thread.sleep(1000);
                        // 事件：要写
                        writeBuff.clear();
                        writeBuff.put("i am server".getBytes());
                        writeBuff.flip();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.write(writeBuff);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void ClientDemo1() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));
            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);


            // writeBuffer.flip();
            int read=0;
            for (int i = 0; i < 10; i++) {


                writeBuffer.clear();
                writeBuffer.put(("1-hello" + i).getBytes());


                // 管道写 => 读取buffer,
                writeBuffer.flip();
                socketChannel.write(writeBuffer);
                System.out.println("写：" + ("hello" + i));


                // 管道读 => 写入buffer， make buffer ready for writing
                readBuffer.clear();
                socketChannel.read(readBuffer);
                // 读取 buffer
                readBuffer.flip();
                System.out.println("读：");
                while (readBuffer.hasRemaining()){
                    System.out.print((char) readBuffer.get());
                }
                System.out.println();

                Thread.sleep(1000);
            }


        } catch (IOException e) {
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
