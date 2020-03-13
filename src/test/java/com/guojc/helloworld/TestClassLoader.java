package com.guojc.helloworld;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class TestClassLoader {
	public static void main(String[] args) throws URISyntaxException, MalformedURLException {
		System.out.println(TestClassLoader.class
				.getResource("log4j2.properties"));
		
		System.out.println(TestClassLoader.class
				.getResource("/log4j2.properties").getPath());
		
		System.out.println();
		
		System.out.println(TestClassLoader.class.getClassLoader().getResource(
				"log4j2.properties"));
		
		System.out.println(TestClassLoader.class.getClassLoader().getResource(
				"/log4j2.properties"));
		
		
		
		System.out.println("-----------------------------------");
		
		
		
		System.out.println(TestClassLoader.class
				.getResource("log4j3.properties").getPath().substring(1));
		
		System.out.println(TestClassLoader.class
				.getResource("/log4j3.properties"));
		
		System.out.println();
		
		System.out.println(TestClassLoader.class.getClassLoader().getResource(
				"log4j3.properties"));
		
		System.out.println(TestClassLoader.class.getClassLoader().getResource(
				"/log4j3.properties"));
	}

}
