package vell.bibi.vvip.util;

public class StrUtil {
	public static boolean isEmpty(String str) {
		if(str == null) return true;
		else if(str.trim().equals("")) return true;
		else return false;
	}
	
	/**
	 * null equals " "
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		if(isEmpty(str1) && isEmpty(str1)) return true;
		if(isEmpty(str1) || isEmpty(str1)) return false;
		return str1.equals(str2);
	}
	
	public static boolean isNumber(String str) {
		char[] charArray = str.toCharArray();
		boolean isNum = true;
		for(char c : charArray){
			if(c < '0' || c > '9') isNum = false;
		}
		return isNum;
	}
}
