import java.util.HashMap;
import java.util.Map;

import utils.JsonUtil;


public class TestJson {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "lirui");
		map.put("age", 28);
		System.out.println(JsonUtil.toJsonString(map));
		Map<String, Object> bigmap = new HashMap<String, Object>();
		bigmap.put("schema", JsonUtil.toJsonString(map));
		System.out.println(JsonUtil.toJsonString(bigmap));
	}
}
