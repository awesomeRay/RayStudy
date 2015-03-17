package netty.objio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import model.Person;

public class ObjectServer {
	 /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = 8001;
    
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            // 只能有一个child handler吗
            b.childHandler(new ObjectInitializer(new ObjectServerHandler()));
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            // 监听服务器关闭监听， sync调用wait直到监听到连接关闭()？？？应该有notify结束此线程的无限循环wait
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
        	// sync执行完表示没有连接了，可以把
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class ObjectServerHandler extends SimpleChannelInboundHandler<Person> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
		System.out.println("server read0 :"+ msg);
		ctx.writeAndFlush("i am server, i read your msg \n");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server active");
		ctx.writeAndFlush(new Person("server active", 1));
		super.channelActive(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server read:" + msg);
		super.channelRead(ctx, msg);
	}
}
