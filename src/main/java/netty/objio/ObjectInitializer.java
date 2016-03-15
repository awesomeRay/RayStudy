package netty.objio;

import io.netty.channel.ChannelHandlerContext;
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
		
		pipeline.addLast("my", new MyHandler());
		System.out.println(1111);
	}

}
class MyHandler extends SimpleChannelInboundHandler<Person> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
		System.out.println("my hander read0 :"+ msg);
		ctx.writeAndFlush("i am my hander, i read your msg \n");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("my hander active");
		ctx.writeAndFlush(new Person("my hander active", 1));
		super.channelActive(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("my hander read:" + msg);
		super.channelRead(ctx, msg);
	}
}