package com.ashelin.chat.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    private static final List<Channel> channeLs = new ArrayList<>();
    private  String clientName;
    private static int newClientIndex = 1;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected" + ctx);
        channeLs.add(ctx.channel());
        clientName = "Client #" + newClientIndex;
        newClientIndex++;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if(s.startsWith("/")){
            if(s.startsWith("/changename")) {
                clientName = s.split("\\s", 2)[1];
            }
            return;
        }
        System.out.println("New Message " + s);
        String out = String.format("[%s]: %s\n", clientName, s);
        for(Channel c : channeLs) {
            c.writeAndFlush(out);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client " + clientName + "leaved");
        channeLs.remove(ctx.channel());
        ctx.close();
    }
}
