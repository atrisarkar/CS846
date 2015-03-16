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
		formatUrl("https://api.github.com/repos/elasticsearch/elasticsearch/commits/136d36b724dabbe9c84ca9dac6901cef2c712fd2");
	}

	public static String formatUrl(String url) {
		return url.substring(22);
		
	}
	
	public static String encodeHTML(String s)
	{
	    StringBuffer out = new StringBuffer();
	    for(int i=0; i<s.length(); i++)
	    {
	        char c = s.charAt(i);
	        if(c > 127 || c=='"' || c=='<' || c=='>')
	        {
	           out.append("&#"+(int)c+";");
	        } else if(c=='\n'){
	           out.append("<br>");
	        }
	        else
	        {
	            out.append(c);
	        }
	    }
	    return out.toString();
	}

}
