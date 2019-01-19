package com.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author rtw
 * @since 2018/11/9
 */
public class NettyServer {
    private int port = 8080;

    public void beginServer() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(); //1
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); //2
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) //3
                    .childHandler(new ChannelInitializer<SocketChannel>() { //4
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 其就是按行解析数据，在遇到字符\n、\r\n的时候，就认为是一个完整的数据包
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync(); //5
            System.out.println("开始监听" + port + "端口");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().beginServer();
    }
}
