package grammer;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.util.CollectionsUtils;

import com.bestv.lego.utils.JsonUtil;

public class TestMap {
	public static void test1() {
		MyKey key = new MyKey("lirui");
		MyValue value = new MyValue("nan");
		Map<MyKey, MyValue> map = new HashMap<MyKey, MyValue>();
		map.put(key, value);
		System.out.println(JsonUtil.generateJson(map));
		key.name = "lirui1";
		value.sex = "nan1";
		map.put(key, value);
		System.out.println(JsonUtil.generateJson(map));
	}
	public static void main(String[] args) {
		test1();
	}
}
class MyKey {
	String name;

	public MyKey(String name) {
		super();
		this.name = name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
class MyValue {
	String sex;
	
	public MyValue(String sex) {
		super();
		this.sex = sex;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return sex;
	}
}
