package grammer.multithread;

public class RunnableAndThread {
	public static void test1() {
		Runnable run1 = new MyRunnable();
		new Thread(run1).start();
		new Thread(run1).start();
		new Thread(run1).start();
	}
	public static void test2() {
		MyThread thread1 = new MyThread();
		thread1.start();
		thread1.start();
		new Thread(thread1).start();
		new Thread(thread1).start();
		new Thread(thread1).start();
	}
	public static void main(String[] args) {
		test2();
	}

}
class MyRunnable implements Runnable {
	private int count;
	public void run() {
		System.out.println("" + count++);
		
	}
}
class MyThread extends Thread {
	private int count;
	public void run() {
		System.out.println("" + count++);
		
	}
}