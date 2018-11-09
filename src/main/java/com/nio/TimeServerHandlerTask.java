package com.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;

/**
 * @author rtw
 * @since 2018/11/7
 */
public class TimeServerHandlerTask implements Runnable{
    SocketChannel socketChannel;
    ExecutorService executorService;
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2048 * 5);


    public TimeServerHandlerTask(SocketChannel socketChannel, ExecutorService executorService) {
        this.socketChannel = socketChannel;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        try {
            //把系统的数据读取到新建的缓冲区中
            if (socketChannel.read(byteBuffer)>0){
                while (true){
                    //把读状态改为写状态
                    byteBuffer.flip();
                    if (byteBuffer.remaining() < "GET CURRENT TIME".length()){
                        //当读取到的数据长度小于"GET CURRENT TIME"时，表示没有读完，压缩后再次读取数据
                        byteBuffer.compact();
                        socketChannel.read(byteBuffer);
                        continue;
                    }
                    byte[] request=new byte[byteBuffer.remaining()];
                    byteBuffer.get(request);
                    String requestStr=new String(request);
                    byteBuffer.clear();
                    if (!"GET CURRENT TIME".equals(requestStr)) {
                        socketChannel.write(byteBuffer.put("BAD_REQUEST".getBytes()));
                    } else {
                        //对缓冲区写入当前时间
                        ByteBuffer byteBuffer = this.byteBuffer.put(LocalDateTime.now().toString().getBytes());
                        byteBuffer.flip();
                        //把缓冲区的信息写入channel
                        socketChannel.write(byteBuffer);
                        byteBuffer.clear();
                    }

                }
            }
            TimeServerHandlerTask currentTask = new TimeServerHandlerTask(socketChannel,
                    executorService);
            executorService.submit(currentTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
