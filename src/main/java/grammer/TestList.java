package grammer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestList {
	public static void test1() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		for (int a : list) {
			System.out.println(a);
			list.remove(a);
		}
		System.out.println(list);
	}
	public static void test2() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()) {
			int a = it.next();
			System.out.println(a);
//			it.remove();
			list.remove(a);
		}
		System.out.println(list);
	}
	public static void main(String[] args) {
		test2();
		List<String> list = new ArrayList<String>() {
			{
				add("1");
			}
		};
	}
}
