package com.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author rtw
 * @since 2018/11/9
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private byte[] req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("now is:" + body);
    }
}
