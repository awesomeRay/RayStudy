package props.test.test.test;
import java.io.InputStream;
import java.util.Properties;


public class TestProps1 {
	public static void main(String[] args) throws Exception{
		Properties prop = new Properties();//属性集合对象      
//		FileInputStream fis = new FileInputStream("../log4j.properties");//属性文件流      
		InputStream fis =  TestProps1.class.getClass().getResourceAsStream("/test.properties");
		prop.load(fis);//将属性文件流装载到Properties对象中
		System.out.println(prop.get("log4j.rootLogger"));
	}
}
