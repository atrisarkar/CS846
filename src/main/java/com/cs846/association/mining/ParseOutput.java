package com.cs846.association.mining;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.cs846.code.json.FileEntry;

import ca.pfv.spmf.algorithms.associationrules.agrawal94_association_rules.AssocRule;

public class ParseOutput {
	
	
	@Override
	public String toString() {
		StringBuilder rules = new StringBuilder();
		for(String s : prettyRules) {
			rules.append(s);
		}
		return rules.toString();
	}

	List<String> prettyRules;
	
	public List<String> getPrettyRules() {
		return prettyRules;
	}

	public ParseOutput(AssocRule rules) {
		String ruleString = rules.toString();
		Scanner strScanner = new Scanner(ruleString).useDelimiter("\n");
		while(strScanner.hasNext()) {
			String line = strScanner.next();
			if(line.contains("==>")) {
				prettyRules.add(line);
			}
		}
	}

	public static Set<FileEntry> parse(String string) {
		
		Scanner scanner = new Scanner(string).useDelimiter("\n");
		Set<FileEntry> allFiles = new HashSet<FileEntry>();
		Set<String> precedents = new HashSet<String>();
		Map<String,Float> supMap = new HashMap<String,Float>();
		Map<String,Float> confMap = new HashMap<String,Float>();
		while(scanner.hasNext()) {
			String rulePrecedents = scanner.next().split("==>")[1].trim();
			String[] files = rulePrecedents.substring(0,rulePrecedents.indexOf("#")).split(" ");
			String support = rulePrecedents.substring(rulePrecedents.indexOf(":")+1,rulePrecedents.lastIndexOf("#")).trim();
			String confidence = rulePrecedents.substring(rulePrecedents.lastIndexOf(":")+1,rulePrecedents.length()).trim();
			
			
			for(String s : files ){
				if(precedents.add(s)){
					supMap.put(s,Float.valueOf(support));
					confMap.put(s,Float.valueOf(confidence));
				} else {
					if(supMap.get(s)<Float.valueOf(support)) {
						supMap.put(s,Float.valueOf(support));
					}
					if(confMap.get(s)<Float.valueOf(confidence)) {
						confMap.put(s,Float.valueOf(confidence));
					}
				}
				
			}
		}
			for(String s : precedents){
				FileEntry fe = new FileEntry();
				fe.setFile(s);
				fe.setConfidence(confMap.get(s).toString());
				fe.setSupport(supMap.get(s).toString());
				allFiles.add(fe);
			}
			
		
		return allFiles;
	}
	
	

}
