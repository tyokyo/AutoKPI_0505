package com.event;

import java.io.IOException;

public class Data {
	public void runEvent(){
		try {
			Runtime.getRuntime().exec("adb shell getevent -l <");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		
	}
}
