package org.example.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.LogEvent;

/**
 * @author by hyb
 * @date 2022/4/15 0:27
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(msg.getReceived());
        sb.append(" [");
        sb.append(msg.getSource().toString());
        sb.append("] [");
        sb.append(msg.getLogfile());
        sb.append("] : ");
        sb.append(msg.getMsg());
        System.out.println(sb.toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
