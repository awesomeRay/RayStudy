package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class BaiduReader {  
    private Charset charset = Charset.forName("gbk");// 创建GBK字符集  
    {
    	System.out.println(charset.encode("你好123"));
    }
    private SocketChannel channel;  
    public void readHTMLContent() {  
        try {  
            InetSocketAddress socketAddress = new InetSocketAddress("www.fun.tv", 80);
//        	InetSocketAddress socketAddress = new InetSocketAddress(80);
//step1:打开连接  
            channel = SocketChannel.open(socketAddress);  
        //step2:发送请求，使用GBK编码  
            channel.write(charset.encode("GET " + "http://www.fun.tv/ HTTP/1.1" + "\r\n\r\n"));  
//            channel.write(charset.encode("HOST: " + " www.baidu.com" + "\r\n\r\n"));  
            //step3:读取数据  
            
            ByteBuffer buffer = ByteBuffer.allocate(1024);// 创建1024字节的缓冲  
            while (channel.read(buffer) != -1) {
            	System.out.println("--------" + buffer.position() + "----" + buffer);
            	/* 
            	 * 为什么要调flip，因为channel.read(buffer),对buffer就是写操作，写够了1024或直接读完了channel内容
            	 * position移动到的地方就是这次读到limit，因此要读buffer内容，就要把flip,使得limit=position, position=0
            	 * 可以总结为，channel.read(buffer)肯定要flip一下，如果不，flip，除非buffer还没写满，否则超过了position肯定>1024 就overflow/arrayIndexOutofBounds了
            	 */
                buffer.flip();// flip方法在读缓冲区字节操作之前调用。  
                // 使用Charset.decode方法将字节转换为字符串  
                System.out.println(charset.decode(buffer));  
                
                buffer.clear();// 清空缓冲  
            }
        } catch (Exception e) {  
        	e.printStackTrace();
        } finally {  
            if (channel != null) {  
                try {  
                    channel.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
    }  
    public static void main(String[] args) {  
        new BaiduReader().readHTMLContent();  
    }  
}  