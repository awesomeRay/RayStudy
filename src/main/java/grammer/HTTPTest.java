package grammer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class HTTPTest {
	public static void testEncodeDecode() {
		try {
			String s = "d1K3Xc5R3jFdYQE4ZQTaaMGUeMbsVIq78e2n9dYBpe+B3a5urSN3T1uu5sl1zJoFnRH0b++H0YxE/OWjQ9rZiSJ9XW+JC645S0BRwGveVl/JZcwL7ZRzxtr01S+z9JYnbT8UuF8Hi2GefKTl1pod5Kg3GBl1/LJJn/DkTPQm3EQ=";
			String s1 = URLDecoder.decode("d1K3Xc5R3jFdYQE4ZQTaaMGUeMbsVIq78e2n9dYBpe%2BB3a5urSN3T1uu5sl1zJoFnRH0b%2B%2BH0YxE%2FOWjQ9rZiSJ9XW%2BJC645S0BRwGveVl%2FJZcwL7ZRzxtr01S%2Bz9JYnbT8UuF8Hi2GefKTl1pod5Kg3GBl1%2FLJJn%2FDkTPQm3EQ%3D");
			System.out.println(s1);
			System.out.println(s.equals(s1));
			System.out.println(URLEncoder.encode("This string has spaces","UTF-8"));
			System.out.println(URLDecoder.decode("This+string+has+spaces","UTF-8"));
			
			System.out.println(URLEncoder.encode("This*string*has*asterisks","UTF-8"));
			System.out.println(URLEncoder.encode("This%string%has%percent%signs", "UTF-8"));
			System.out.println(URLEncoder.encode("This+string+has+pluses","UTF-8"));
			System.out.println(URLEncoder.encode("This/string/has/slashes","UTF-8"));
			System.out.println(URLEncoder.encode("This:string:has:colons","UTF-8"));
			System.out.println(URLEncoder.encode("This~string~has~tildes","UTF-8"));
			System.out.println(URLEncoder.encode("This(string)has(parentheses)", "UTF-8"));
			System.out.println(URLEncoder.encode("This.string.has.periods","UTF-8"));
			System.out.println(URLEncoder.encode("This=string=has=equals=signs", "UTF-8"));
			System.out.println(URLEncoder.encode("This&string&has&ersands","UTF-8"));
			System.out.println(URLEncoder.encode("Thiséstringéhasé non-ASCII characters","UTF-8"));
			System.out.println(URLEncoder.encode("this中华人民共和国","UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Broken VM does not support UTF-8");
		}
	}
	/** fiddler是通过代理端口8888来监听网络请求的 */
	public static void setProxy() throws Exception {
        // set http proxy
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "8888");
        // set https proxy
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "8888");
        
    }

    private static void doRequest() {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://www.baidu.com");
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setRequestMethod("GET"); // 设定请求方式
            conn.connect();

            InputStream in = conn.getInputStream();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

            // 判断是否正常响应数据
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("错误响应码 " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect(); // 中断连接
            }
        }
        
    }
    
    public static void testPost() {
    	String data = "loginName=lirui&password=xxx";
        try {
            
            // Send the request
//            URL url = new URL("http://10.201.16.19:8080/sardine-admin/login_dialog");
            URL url = new URL("https://101.231.204.80:5000/gateway/api/backTransReq.do");
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            
            //write parameters
            writer.write(data);
            writer.flush();
            
            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();
            
            //Output the response
            System.out.println(answer.toString());
            
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception{
    	testEncodeDecode();
    	setProxy();
    	doRequest();
	}
}
