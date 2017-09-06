package com.irmms.util;

public class StringUtil {
	public static String parseString(String str)
	{
		if(str == null || str.trim().equalsIgnoreCase(""))
		{
			str = "";
		}
		else
		{
			str=str.trim();
		}
		return str;
	}
}
