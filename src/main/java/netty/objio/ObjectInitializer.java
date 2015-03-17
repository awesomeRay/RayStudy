package netty.objio;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import model.Person;

public class ObjectInitializer extends ChannelInitializer<SocketChannel>{
	
	private SimpleChannelInboundHandler<Person> handler;
	
	public ObjectInitializer(SimpleChannelInboundHandler<Person> handler) {
		this.handler = handler;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		if (handler == null) {
			throw new RuntimeException("handler is not settled yet");
		}
		ChannelPipeline pipeline = ch.pipeline();
		
		// 以("\n") or ("\r\n")为结尾分割的 解码器
//		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new MyObjectDecoder());
		pipeline.addLast("encoder", new MyObjectEncoder());
		// 逻辑handler
		pipeline.addLast("handler", handler);
		
	}

}
