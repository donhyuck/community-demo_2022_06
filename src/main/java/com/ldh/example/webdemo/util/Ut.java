package com.ldh.example.webdemo.util;

public class Ut {

	public static boolean empty(Object obj) {

		if (obj == null) {
			return true;
		}

		if (obj instanceof String == false) {
			return true;
		}

		String str = (String) obj;

		return str.trim().length() == 0;
	}

	public static Object f(String msg, Object... args) {
		return String.format(msg, args);
	}
}
