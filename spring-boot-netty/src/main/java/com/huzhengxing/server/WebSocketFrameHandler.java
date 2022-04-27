package com.huzhengxing.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Locale;

/**
 * @author - 2022/3/3 21:22 albert
 * @version - 2022/3/3 21:22 1.0.0
 * @file WebSocketFrameHandler
 * @brief 文件简要说明
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 * ©2019 杭州锘崴信息科技有限公司 版权所有
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled
        System.out.println("Server received  message frame client  is " + ((TextWebSocketFrame) frame).text());
        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            System.out.println("request is " + request);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}