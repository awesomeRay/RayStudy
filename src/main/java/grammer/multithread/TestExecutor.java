package grammer.multithread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestExecutor {
	public static AtomicInteger counter = new AtomicInteger(0);

	public static void test1() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println(counter.incrementAndGet());
			}
		}, 0, 2, TimeUnit.SECONDS);
	}

	public static void test2() {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		// Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		for (String key : map.keySet()) {
			System.out.println(key);
			map.remove(key);
		}
	}

	public static void test3() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					// sleep一秒后，下一个线程启动时前一个已经结束，所以始终用的是同一个线程
					System.out.println(Thread.currentThread().getName());
				}
			});
		}
	}

	public static void main(String[] args) {
		test3();
	}
}
