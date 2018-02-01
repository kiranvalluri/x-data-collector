package com.adroit.datacollector.vo;

public class AppUtil {
	
	public static boolean isValidString(String input) {

		if (null != input && !"".equals(input)) {

			return true;

		} else {
			return false;
		}
	}

}
