package grammer;

public class TestHeritate {
	public static void main(String[] args) {
		B b = new B();
		b.setAge(2);
		b.print();
	}
}
class A {
	private transient int age = 0;
	public void setAge(int age) {
		this.age = age;
	}
	public void print() {
		System.out.println("i am " + age + " years old");
	}
}
class B extends A {
	
}