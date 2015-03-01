package com.cs846.association.mining;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

	public static Set<String> parse(String string) {
		Scanner scanner = new Scanner(string).useDelimiter("\n");
		Set<String> precedents = new HashSet<String>();
		while(scanner.hasNext()) {
			String rulePrecedents = scanner.next().split("==>")[1].trim();
			String[] files = rulePrecedents.split(" ");
			for(String s : files ){
				precedents.add(s);
			}
		}
		return precedents;
	}
	
	

}
