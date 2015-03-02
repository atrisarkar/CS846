package com.cs846.code.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Temp {

	public static void main(String[] args) {
		Gson g = new Gson();
		XChangeFile in = new XChangeFile();
		List<String> t = new ArrayList<String>();
    	t.add("check");
    	in.setChangeSets(t);
    	System.out.println(g.toJson(in));
	}
}
