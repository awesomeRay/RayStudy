package netty.stringio;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient {
	public static void main(String args[]) throws Exception{
		// Client服务启动器 3.x的ClientBootstrap 改为Bootstrap，且构造函数变化很大，这里用无参构造。
		Bootstrap bootstrap = new Bootstrap();
		// 指定channel类型
		bootstrap.channel(NioSocketChannel.class);
		// 指定Handler
		bootstrap.handler(new StringInitializer(new HelloClientHandler()));
//		bootstrap.handler(new HelloClientInitializer());
		// 指定EventLoopGroup
		bootstrap.group(new NioEventLoopGroup());
		// 连接到本地的8000端口的服务端
		
		// 连接服务端
		bootstrap.connect(new InetSocketAddress("127.0.0.1", 8001));
		
	}
		
}
class HelloClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("client get message : "+msg);
//		ctx.writeAndFlush("i am client, i read your msg \n");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("active.....");
		// 必须有\n 或者 \r\n , 只有遇到\n才会把buffer中的内容flush到socket
		ctx.writeAndFlush("client active \n");
		ctx.writeAndFlush("client active1 \r\n");
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("client close");
		super.channelInactive(ctx);
	}
	
}

class HelloClientInitializer extends ChannelInitializer<SocketChannel>{
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		// 以("\n") or ("\r\n")为结尾分割的 解码器
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		
		// 字符串编解码
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		
		// 逻辑handler
		pipeline.addLast("handler", new HelloClientHandler());
		
	}

}
