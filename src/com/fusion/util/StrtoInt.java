package com.fusion.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StrtoInt {
	public static ArrayList toInteger(String str) {
		List <String> list = new ArrayList <String> ();
		list = Arrays.asList(str.substring(1, str.length()-1).split( ", "));
		ArrayList temp=new ArrayList();
		for(int i=0;i<list.size();i++){
			temp.add(Integer.parseInt(list.get(i)));
		}
		return temp;
	}
}
