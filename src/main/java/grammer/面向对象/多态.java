package grammer.面向对象;

import java.util.ArrayList;
import java.util.List;

public class 多态 {
	public static void test1() {
	
	}
	public static void main(String[] args) {
		List<Father> list1 = new ArrayList<Father>();
		List<Son> list2 = new ArrayList<Son>();
	}
}
class Father {
	
}
class Son extends Father {
	
}