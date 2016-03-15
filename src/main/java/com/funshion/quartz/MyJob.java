package com.funshion.quartz;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component("myJob")
public class MyJob {
	
	@PostConstruct
	public void init() {
		System.out.println("myjob init");
	}
	
	public void print() {
		System.out.println("hello");
	}

}
