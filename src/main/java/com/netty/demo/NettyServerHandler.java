package com.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * @author rtw
 * @since 2018/11/9
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { //1
        String request = (String) msg; //2
        String response = null;
        if ("QUERY TIME ORDER".equals(request)) { // 3
            response = LocalDateTime.now().toString();
        } else {
            response = "BAD REQUEST";
        }
        response = response + System.getProperty("line.separator"); //4
        ByteBuf resp = Unpooled.copiedBuffer(response.getBytes()); //5
        ctx.writeAndFlush(resp); //6
    }
}
