package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ScatterAndGather {
	
	private static Charset charset = Charset.forName("gbk");// 创建GBK字符集  
	
	public static void scatter() throws Exception {  
        String infile = "C:/123.txt";  
        String outfile = "C:/1231.txt";
        // 获取源文件和目标文件的输入输出流  
        FileInputStream fin = new FileInputStream(infile);  
        FileOutputStream fout = new FileOutputStream(outfile);  
        // 获取输入输出通道  
        FileChannel fcin = fin.getChannel();  
        FileChannel fcout = fout.getChannel();  
        // 创建缓冲区  
        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffer = {buffer1, buffer2};
        fcin.read(buffer);
        buffer1.flip(); // fcout.write(buffer)和charset.decode都是读buffer，所以都需要flip
        System.out.println(charset.decode(buffer1));
        System.out.println("------");
        buffer2.flip();
        System.out.println(charset.decode(buffer2));
        buffer1.flip();buffer2.flip();
        fcout.write(buffer);
    }  
	
	public static void main(String[] args) throws Exception {
		scatter();
	}
}
