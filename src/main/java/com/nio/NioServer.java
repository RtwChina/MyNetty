package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author rtw
 * @since 2018/11/7
 */
public class NioServer {
    private BlockingQueue<SocketChannel> idleQueue =new LinkedBlockingQueue<SocketChannel>();
    private BlockingQueue<Future<SocketChannel>> workingQueue=new LinkedBlockingQueue<Future<SocketChannel>>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    {
        new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        //task1：迭代当前idleQueue中的SocketChannel，提交到线程池中执行任务，并将其移到workingQueue中
                        for (int i = 0; i < idleQueue.size(); i++) {
                            SocketChannel socketChannel = idleQueue.poll();
                            if (socketChannel != null) {
                                Future<SocketChannel> result = executor.submit(new TimeServerHandlerTask(socketChannel,executor), socketChannel);
                                workingQueue.put(result);
                            }
                        }
                        //task2：迭代当前workingQueue中的SocketChannel，如果任务执行完成，将其移到idleQueue中
                        for (int i = 0; i < workingQueue.size(); i++) {
                            //根据每个线程的Future来进行判断线程是否运行完毕，如果运行完毕则SocketChannel添加到idleQueue队列中。
                            Future<SocketChannel> future = workingQueue.poll();
                            if (future != null && !future.isDone()) {
                                workingQueue.put(future);
                                continue;
                            }
                            if (future == null) {
                                break;
                            }
                            SocketChannel channel  = null;
                            try {
                                // future.get() 等待执行完毕并拿到结果
                                channel = future.get();
                                idleQueue.put(channel);
                            } catch (ExecutionException e) {
                                //如果future.get()抛出异常，关闭SocketChannel，不再放回idleQueue
                                channel.close();
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        NioServer nioServer = new NioServer();
        ServerSocketChannel ssc=ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8080));
        while (true) {
            // 疯狂循环accept,当获得一个SocketChannel之后把其设置为非阻塞然后放到idleQueue中
            SocketChannel socketChannel = ssc.accept();
            if (socketChannel == null){
                continue;
            } else {
                socketChannel.configureBlocking(false);
                nioServer.idleQueue.add(socketChannel);
            }
        }
    }
}
