package com.myNetty.client;

import com.myNetty.handler.SimpleClientHandler;
import com.netty.demo.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rtw
 * @since 2019/2/24
 */
@Slf4j
public class NettyClient {

    static final Bootstrap b = new Bootstrap(); // (1)

    static ChannelFuture f= null;

    static {
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.handler(new ChannelInitializer<SocketChannel>() {// (4)
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new SimpleClientHandler());
                    ch.pipeline().addLast(new StringEncoder());
                }
            });
            // Start the client.
            f = b.connect(host, port).sync(); // (5)
            f.channel().writeAndFlush("hello Server\r\n");

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            System.out.println("client 通道关闭");
        } catch (Exception e) {
            log.error("Client启动异常，e={}", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new NettyClient().beginClient();
    }
    public void beginClient() throws Exception {

    }
}
