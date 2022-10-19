package com.ashelin.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private CallBack onMessageReceivedCallback;

    public ClientHandler(CallBack onMessageReceivedCallback) {
        this.onMessageReceivedCallback = onMessageReceivedCallback;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if(onMessageReceivedCallback != null ) {
            onMessageReceivedCallback.callback(s);
        }
    }
}
