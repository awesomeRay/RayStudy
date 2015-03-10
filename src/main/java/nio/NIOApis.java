package nio;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOApis {

	/**
	 * 1 从fromChannel拷贝到toChannel中，并完成toChannel的写操作(比如写入文件) 2 transerFrom
	 * transerTo 的from和to若是同一个channel不起作用
	 * */
	public static void transerFrom() throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile("f:/1.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("f:/2.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 2;
		long count = fromChannel.size();

		// position等于非0的时候，怎么都是toFile是空的， 为什么？？
		toChannel.transferFrom(fromChannel, position, 2);
		// RandomAccessFile写文件是从头开始写，一个字节一个字节的向后码
	}

	public static void transerTo() throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile("f:/1.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("f:/2.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 0;
		long count = fromChannel.size();
		System.out.println(count);

		fromChannel.transferTo(position, 4, toChannel);
	}

	// TODO SocketChannel待实验
	public static void testSocketChannel() {
	}

	public static void testSelector() throws Exception {
		Selector selector = Selector.open();
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("172.16.12.52", 8080));
		socketChannel.configureBlocking(false);
		SelectionKey key1 = socketChannel.register(selector,
				SelectionKey.OP_READ, "52");

		SocketChannel socketChannel1 = SocketChannel.open();
		socketChannel1.connect(new InetSocketAddress("172.16.12.54", 8080));
		socketChannel1.configureBlocking(false);
		SelectionKey key2 = socketChannel1.register(selector,
				SelectionKey.OP_READ, "54");

		System.out.println(key1.selector() == key2.selector());
		System.out.println(selector.selectedKeys().size()); // 为什么在这里是空
		while (true) {
			int readyChannels = selector.select();
			if (readyChannels == 0)
				continue;
			Set selectedKeys = selector.selectedKeys();
			Iterator keyIterator = selectedKeys.iterator();
			int i = 0;
			while (keyIterator.hasNext()) {
				System.out.println("hhh" + (++i));
				SelectionKey key = (SelectionKey) keyIterator.next();
				if (key.isAcceptable()) {
					// a connection was accepted by a ServerSocketChannel.
				} else if (key.isConnectable()) {
					// a connection was established with a remote server.
					System.out.println(3);
				} else if (key.isReadable()) {
					// a channel is ready for reading
					System.out.println(1);
				} else if (key.isWritable()) {
					// a channel is ready for writing
					System.out.println(2);
				}
				keyIterator.remove();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		testSelector();
	}
}
