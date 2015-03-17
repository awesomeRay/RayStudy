package model;

public class Person {
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	Person() {}
	private int id;
	private String name;
	private int age;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "name : " + name + ", age : " + age;
	}
}
