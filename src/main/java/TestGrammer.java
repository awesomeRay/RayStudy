import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class TestGrammer {
	public static void test1() {
		System.out.println(Long.parseLong("1.0"));
		
	}
	
	public static void test2() {
		String s = "1";
		Object o = s;
		o = "2";
		System.out.println(s);
		System.out.println(o);
	}
	
	public static void test3() {
		Pattern pattern = Pattern.compile("[A-Z].*");
		String s = "Xjvmkafka.metr.1";
		Matcher matcher = pattern.matcher(s);
		System.out.println(matcher.matches());
	}
	
	public static void test4() {
		double a = 1;
		for (int i = 0 ; i < 15 ; i++) {
			a *= 1.1;
		}
		System.out.println(a);
		
	}
	
	public static void test5() {
		String path = "/test/hello";
		if (StringUtils.isNotBlank(path)) {
			String name = StringUtils.removeStart(path.replace('/', '_'), "_");
			System.out.println(name);
		}
	}
	
	public static void test7() throws Exception{
		// 运行结果：2
		System.out.println("测试".getBytes("ISO8859-1").length);
		// 运行结果：4
		System.out.println("测试".getBytes("GB2312").length);
		// 运行结果：4
		System.out.println("测试".getBytes("GBK").length);
		// 运行结果：6
		System.out.println("测试".getBytes("UTF-8").length);
	}
	
	public static void getInetAddress() throws Exception {  
		System.out.println(InetAddress.getLocalHost());  
		InetAddress netAddress = InetAddress.getLocalHost();  
        System.out.println("host ip:" + netAddress.getHostAddress());  
        System.out.println("host name:" + netAddress.getHostName()); 
        
        try {   
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();   
               
            while (networkInterfaces.hasMoreElements()) {   
                NetworkInterface networkInterface = networkInterfaces.nextElement();   
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();   
                while (inetAddresses.hasMoreElements()) {   
                    InetAddress inetAddress = inetAddresses.nextElement();   
                    if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().indexOf(":") == -1) {
                    	System.out.println(inetAddress.getHostAddress());
                    	System.out.println(inetAddress.getHostName());
                    }
                }   
            }   
               
        } catch (SocketException e) {   
            throw new RuntimeException(e.getMessage(), e);   
        }   
    }   
	
	public static void main(String[] args) throws Exception{
		getInetAddress();
	}
}
