package com.dz.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by RoyDeng on 17/7/11.
 */
@ChannelHandler.Sharable                                        //1
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));        //2
//        Object returnMsg = "returnSuccess";
         ByteBuf out = Unpooled.copiedBuffer("returnSuccess", //2
                CharsetUtil.UTF_8);
//        ByteBuf out = (ByteBuf)returnMsg;
        ctx.write(out);                            //3
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//4
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();                //5
        ctx.close();                            //6
    }
}
