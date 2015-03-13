package netty.stringio;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class StringInitializer extends ChannelInitializer<SocketChannel>{
	
	private SimpleChannelInboundHandler<String> handler;
	
	public StringInitializer(SimpleChannelInboundHandler<String> handler) {
		this.handler = handler;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		if (handler == null) {
			throw new RuntimeException("handler is not settled yet");
		}
		ChannelPipeline pipeline = ch.pipeline();
		
		// 以("\n") or ("\r\n")为结尾分割的 解码器
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		
		// 字符串编解码
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		
		// 逻辑handler
		pipeline.addLast("handler", handler);
		
	}

}
