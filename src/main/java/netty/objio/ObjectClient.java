package netty.objio;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import model.Person;

public class ObjectClient {
	public static void main(String args[]) throws Exception{
		// Client服务启动器 3.x的ClientBootstrap 改为Bootstrap，且构造函数变化很大，这里用无参构造。
		Bootstrap bootstrap = new Bootstrap();
		// 指定channel类型
		bootstrap.channel(NioSocketChannel.class);
		// 指定Handler
		bootstrap.handler(new ObjectInitializer(new ObjectClientHandler()));
//		bootstrap.handler(new HelloClientInitializer());
		// 指定EventLoopGroup
		bootstrap.group(new NioEventLoopGroup());
		
		// 连接服务端
		bootstrap.connect(new InetSocketAddress("127.0.0.1", 8001));
		
	}
		
}
class ObjectClientHandler extends SimpleChannelInboundHandler<Person> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
		System.out.println("client read0 message : "+msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client active.....");
		ctx.writeAndFlush(new Person("client", 2));
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("client close");
		super.channelInactive(ctx);
	}
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("client read person : " + ((Person)msg).getName());
		super.channelRead(ctx, msg);
	}
	
}
