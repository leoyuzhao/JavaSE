package com.yzf.study.io.nio.filelock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFileLock {

    public static void main(String[] args) throws IOException {
        String input = "test file lock";
        System.out.println("输入 :" + input);
        ByteBuffer buf = ByteBuffer.wrap(input.getBytes());
        String fp = "D:\\test\\a.txt";
        Path pt = Paths.get(fp);

        FileChannel channel = FileChannel.open(pt, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        // position of a cursor at the end of file
        channel.position(channel.size() - 1);

        // 获得锁方法一：lock()，阻塞方法，当文件锁不可用时，当前进程会被挂起//lock = channel.lock();// 无参 lock()为独占锁
        // lock = channel.lock(0L, Long.MAX_VALUE, true);//有参lock()为共享锁，有写操作会报异常
        // 获得锁方法二：trylock()，非阻塞的方法，当文件锁不可用时，tryLock()会得到 null 值
        FileLock lock = channel.tryLock(0, Long.MAX_VALUE, false);
        System.out.println("共享锁 shared: " + lock.isShared());
        channel.write(buf);
        channel.close();
        // Releases the Lock
        System.out.println("写操作完成.");
        //读取数据
        readPrint(fp);
    }

    public static void readPrint(String path) throws IOException {
        FileReader filereader = new FileReader(path);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String tr = bufferedreader.readLine();
        System.out.println("读取内容: ");
        while (tr != null) {
            System.out.println(" " + tr);
            tr = bufferedreader.readLine();
        }
        filereader.close();
        bufferedreader.close();
    }

}
