package com.cs846.association.mining;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.AlgoTopKRules;
import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.Database;
import ca.pfv.spmf.algorithms.associationrules.agrawal94_association_rules.AlgoAgrawalFaster94;
import ca.pfv.spmf.algorithms.associationrules.agrawal94_association_rules.AssocRule;
import ca.pfv.spmf.algorithms.associationrules.agrawal94_association_rules.AssocRules;
import ca.pfv.spmf.algorithms.frequentpatterns.apriori_inverse.AlgoAprioriInverse;
import ca.pfv.spmf.algorithms.frequentpatterns.cfpgrowth.AlgoCFPGrowth;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import ca.pfv.spmf.test.MainTestAllAssociationRules_CFPGrowth_saveToMemory;
import ca.pfv.spmf.test.MainTestAllPerfectlySporadicAssociationRules_Apriori_saveToMemory;

public class RuleEngine {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		RuleEngine engine = new RuleEngine();
		StringBuilder b = new StringBuilder();
		b.append("1 2 3 4 5 6 ");
		b.append("\n");
		b.append("1 3 6 7 8 ");
		b.append("\n");
		b.append("1 ");
		b.append("\n");
		b.append("1 4 9 10 11 12 13 14 15 16 ");
		b.append("\n");
		b.append("1 14 15 16 17 18 ");
		b.append("\n");
		String testTrans = b.toString();
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(1);
		engine.setTransactions(testTrans);
		engine.setRootFile(temp);
		engine.getRules();

	}
	
	String transactions;
	
	List<Integer> rootFile;

	public void setRootFile(List<Integer> rootFile) {
		this.rootFile = rootFile;
	}

	public String getTransactions() {
		return transactions;
	}

	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}

	public Set<String> getRules() throws FileNotFoundException, IOException {

		
			// Loading the binary context
			if(transactions == null) {
				throw new FileNotFoundException();
			}
			String input = transactions;
		
			// Load database into memory
			Database database = new Database(); 
			database.loadFile(input,false); 
			
			int k = 10; 
			double minConf = 0.1; //
			
			AlgoTopKRules algo = new AlgoTopKRules();
			algo.setRoot(rootFile);
			algo.runAlgorithm(k, minConf, database);

			//algo.printStats();
			
			System.out.println(algo.toString());
			return ParseOutput.parse(algo.toString());
		
		
}

	private String fileToPath(String filename) throws UnsupportedEncodingException {
		URL url = RuleEngine.class.getResource(filename);
		return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}

}
