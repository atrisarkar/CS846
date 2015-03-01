package com.cs946.code.api;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.GitHubResponse;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.GitHubService;

import com.cs846.association.mining.RuleEngine;
import com.cs846.code.json.XChangeFile;
import com.cs846.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


public class GitHubAPI {

	public static void main(String[] args) {
		List<String> input = new ArrayList<String>();
		input.add("src/main/java/org/elasticsearch/cluster/action/index/NodeIndexDeletedAction.java");
		GitHubAPI api = new GitHubAPI();
		XChangeFile xch = new XChangeFile();
		xch.setChangeSets(input);
		api.getRelated(xch);
	}
	/**
	 * @param args
	 * @return 
	 */
	public XChangeFile getRelated(XChangeFile inData) {
		try {
			List<String> input = inData.getChangeSets();
			GitHubClient client = new GitHubClient();
			client.setCredentials("atrisarkar", "cs846access");
			//client.setOAuth2Token(GitHubConstants.OAUTH);
			StringBuilder transactionString = new StringBuilder();
			CommitService service = new CommitService(client);
			
			IRepositoryIdProvider provider = RepositoryId.createFromId(GitHubConstants.REPOSITORY_PATH);
			
			
			
			int limit = Math.min(input.size(), 10);
			List<RepositoryCommit> commitList = new ArrayList<RepositoryCommit>();
			for(String e : input.subList(0, limit)){
				commitList.addAll(service.getCommits(provider,null,e));
			}
			
			Gson gson = new Gson();
			BidiMap transMap = new DualHashBidiMap();
			Integer counter = 1;
			for(RepositoryCommit c : commitList){
				/*
				//URL oracle = new URL(c.getUrl());
			    //URLConnection yc = oracle.openConnection();
			    //BufferedReader in = new BufferedReader(new InputStreamReader(
			    //                            yc.getInputStream()));
				
				
				GitHubRequest reqForFile = new GitHubRequest();
				reqForFile.setUri(StringUtil.formatUrl(c.getUrl()));
				GitHubResponse response = client.get(reqForFile);
				*/
				
				// We need to do this as getCommits(<>) does not return the list of files :(
				CommitService service1 = new CommitService(client);
				RepositoryCommit r = service1.getCommit(provider, c.getSha());
				
				String tJSON = gson.toJson(r);
				
			    com.cs846.code.json.RepositoryCommit rc = 
			    		gson.fromJson(tJSON, com.cs846.code.json.RepositoryCommit.class);
			    
			    List<com.cs846.code.json.Files> files = rc.getFiles();
			    Integer fileInt;
			    if(files.size()<=10){
			    	for(com.cs846.code.json.Files f : files){
			        	String fileName = f.getFilename();
			        	if(transMap.containsKey(fileName)) {
			        		fileInt = (Integer) transMap.get(fileName);
			        	} else {
			        		transMap.put(fileName, counter);
			        		fileInt = counter;
			        		counter++;
			        		
			        	}
			        	
			        	transactionString.append(fileInt + " ");
			        }
			        transactionString.append("\n");
			    }
			    
			    
			    
			}
			//System.out.println(transactionString.toString());
			//System.out.println("Input index :" + transMap.get(input));
			
			RuleEngine eng = new RuleEngine();
			List<Integer> inputFileList =  new ArrayList<Integer>();
			for(String e : input.subList(0, limit)){
				inputFileList.add((Integer) transMap.get(e));
			}
			eng.setRootFile((inputFileList));
			eng.setTransactions(transactionString.toString());
			Set<String> files = eng.getRules();
			List<String> relatedFiles = new ArrayList<String>();
			for(String s : files) {
				relatedFiles.add((String)transMap.getKey(Integer.valueOf(s)));
				System.out.println(transMap.getKey(Integer.valueOf(s)));
			}
			inData.setRelatedProductFiles(relatedFiles);
			
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return inData;
	}

}
