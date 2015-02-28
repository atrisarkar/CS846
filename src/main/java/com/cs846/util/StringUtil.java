package com.cs846.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static boolean containsRoot(String rule,List<Integer> root) {
		// rule is of the form 1 14 16  ==> 15
		String antecedents = rule.substring(0,rule.indexOf("="));
		String[] items = antecedents.trim().split(" ");
		if(items.length!=1){
			return false;
		}
		for(String s : items){
			Integer sVal = Integer.valueOf(s);
			if(root.contains(sVal)){
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		List<Integer> root = new ArrayList<Integer>();
		root.add(14);
		root.add(1);
		System.out.println(containsRoot("11 1 ==> 16 15",root));
	}

}
