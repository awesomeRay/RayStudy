package nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class 普通TCPServer {
	public static void main(String[] args) {  
        try {  
            ServerSocket ss=new ServerSocket(6000);  
            while(true) {
            	Socket s=ss.accept();
            	OutputStream os=s.getOutputStream();
                InputStream is=s.getInputStream();
                os.write("Hello Welcome you".getBytes());
                byte []buf=new byte[100];
                int len=is.read(buf);
                System.out.println(new String(buf,0,len));
                os.close();
                is.close();
                s.close();
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
}
