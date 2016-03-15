package grammer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
	public static void run1()throws IOException{
        Socket client = new Socket("127.0.0.1" , 5050);
        DataInputStream in = new DataInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        
        out.write("xxxx".getBytes());
        //String response = (String)in.readUTF();
        
        byte[] bytes = new byte[1000];
        in.read(bytes);
        System.out.println("收到:" + new String(bytes));
        out.close();
        in.close();
        client.close();
    }
	
	public static void run2() throws IOException{
		Socket client = new Socket("127.0.0.1" , 5050);
		InputStream in = client.getInputStream();
		OutputStream out = client.getOutputStream();
		
		out.write("hello".getBytes());
		//String response = (String)in.readUTF();
		
		byte[] bytes = new byte[1000];
		System.out.println(in.read(bytes));
		System.out.println("收到:" + new String(bytes));
		out.close();
		in.close();
		client.close();
	}
	public static void main(String[] args) throws Exception{
		run1();
	}
	
}
