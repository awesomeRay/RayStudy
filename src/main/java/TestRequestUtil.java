import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.catalina.util.RequestUtil;


public class TestRequestUtil {
	public static void main(String[] args) throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		String params = "a=3&b=2";
		RequestUtil.parseParameters(map, params, "UTF-8");
		for(Entry<String, String[]> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue()[0]);
		}
		RequestUtil.normalize("");
	}
}
