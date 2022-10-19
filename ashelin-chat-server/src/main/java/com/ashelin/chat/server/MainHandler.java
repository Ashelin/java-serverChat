package com.ashelin.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
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
        broadcastMessage("SERVER", "Connected new client: " + clientName);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("New Message " + s);
        if(s.startsWith("/")){
            if(s.startsWith("/changename")) {
                String newNickname = s.split("\\s", 2)[1] ;
                broadcastMessage("SERVER", "Client " + clientName + "Change his name to: " + newNickname);
                clientName = newNickname;
            }
            return;
        }
        broadcastMessage(clientName, s);

    }

    public void broadcastMessage(String clientName, String message) {

        String out = String.format("[%s]: %s\n", clientName, message);
        for(Channel c : channeLs) {
            c.writeAndFlush(out);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client " + clientName + "dropped");
        channeLs.remove(ctx.channel());
        broadcastMessage("SERVER", "Client " + clientName + "leaved channel");
        ctx.close();
    }
}
