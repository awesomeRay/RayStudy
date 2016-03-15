package grammer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void run1() throws Exception {
		ServerSocket ss = new ServerSocket(5050);
		while (true) {
			Socket s = ss.accept();
			System.out.println("connect");
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println("11111111");
			System.out.println(in.readUTF());
			out.write("bye-bye client".getBytes());
			in.close();
			s.close();
			// System.out.println("a client has connected!");
		}
	}
	
	public static void run2() throws Exception {
		ServerSocket ss = new ServerSocket(5050);
		while (true) {
			Socket s = ss.accept();
			System.out.println("connect");
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			System.out.println("11111111");
			byte[] bytes = new byte[1000];
			in.read(bytes);
			System.out.println("server:" + new String(bytes));
			out.write("bye-bye client".getBytes());
			in.close();
			s.close();
			// System.out.println("a client has connected!");
		}
	}
	
	public static void acceptHttp() throws Exception {
		ServerSocket ss = new ServerSocket(8081);
		while (true) {
			Socket s = ss.accept();
			System.out.println("connect");
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			System.out.println("11111111");
			byte[] bytes = new byte[1000];
			in.read(bytes);
			System.out.println("server:" + new String(bytes));
			out.write("bye-bye client".getBytes());
			in.close();
			s.close();
			// System.out.println("a client has connected!");
		}
	}
	
	public static void main(String[] args) throws Exception {
		run2();
	}
}
