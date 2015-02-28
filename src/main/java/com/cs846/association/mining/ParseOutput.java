package com.cs846.association.mining;

import java.util.List;
import java.util.Scanner;

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
	
	

}
