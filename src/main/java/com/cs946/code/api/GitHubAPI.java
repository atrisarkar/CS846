package com.cs946.code.api;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;

import com.cs846.association.mining.RuleEngine;
import com.cs846.code.json.FileEntry;
import com.cs846.code.json.XChangeFile;
import com.cs846.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


public class GitHubAPI {

	public static void main(String[] args) {
		List<String> input = new ArrayList<String>();
		//input.add("src/main/java/org/elasticsearch/index/store/Store.java");
		//input.add(" src/main/java/org/elasticsearch/indices/recovery/RecoveryStatus.java");
		input.add(".jshintrc");
		input.add("src/js/player.js");
		input.add(" test/unit/plugins.js");
		
		GitHubAPI api = new GitHubAPI();
		XChangeFile xch = new XChangeFile();
		xch.setChangeSets(input);
		xch.setRepository(GitHubConstants.REPOSITORY_PATH);
		xch.setMode("evaluation");
		xch.setCommitHash("/videojs/video.js/commit/a5a8d449189efd7c63f6a61370be32d27e18cba6/show_partial?partial=commits%2Fcommits_list_item");
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
			client.setCredentials("briancanada", "waterloo1");
			//client.setOAuth2Token(GitHubConstants.OAUTH);
			StringBuilder transactionString = new StringBuilder();
			CommitService service = new CommitService(client);
			String repository = inData.getRepository();
			IRepositoryIdProvider provider = RepositoryId.createFromId(repository);
			System.out.println("Repository : "+repository);
			
			
			int limit = Math.min(input.size(), 10);
			List<RepositoryCommit> commitList = new ArrayList<RepositoryCommit>();
			
			String mode = inData.getMode();
			
			if("evaluation".equals(mode)) {
				System.out.println("Running mode : evaluation");
				String commitHash = StringUtil.stripHash(inData.getCommitHash());
				CommitService service1 = new CommitService(client);
				RepositoryCommit r = service1.getCommit(provider, commitHash);
				Gson gson = new Gson();
				String tJSON = gson.toJson(r);
				com.cs846.code.json.RepositoryCommit rc = 
			    		gson.fromJson(tJSON, com.cs846.code.json.RepositoryCommit.class);
			    
			    List<com.cs846.code.json.Files> files = rc.getFiles();
			    List<String> filesForFirstCommit = new ArrayList<String>();
			    for(com.cs846.code.json.Files f : files) {
			    	filesForFirstCommit.add(f.getFilename());
			    }
			    input.clear();
			    input.addAll(filesForFirstCommit);
			    limit = Math.min(input.size(), 10);
			} else {
				System.out.println("Running mode : demo");
			}
			
				
				for(String e : input.subList(0, limit)){
					commitList.addAll(service.getCommits(provider,null,e));
				}
			
			
			Gson gson = new Gson();
			BidiMap transMap = new DualHashBidiMap();
			Map<String,String> messageMap = new HashMap<String,String>();
			Integer counter = 1;
			for(RepositoryCommit c : commitList){
				
				// We need to do this as getCommits(<>) does not return the list of files :(
				CommitService service1 = new CommitService(client);
				RepositoryCommit r = service1.getCommit(provider, c.getSha());
				
				String tJSON = gson.toJson(r);
				
			    com.cs846.code.json.RepositoryCommit rc = 
			    		gson.fromJson(tJSON, com.cs846.code.json.RepositoryCommit.class);
			    
			    List<com.cs846.code.json.Files> files = rc.getFiles();
			    String message = rc.getCommit().getMessage() + "[" +rc.getCommit().getUrl() +"]";
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
			        	messageMap.put(fileInt.toString(), StringUtil.encodeHTML(message));
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
			Set<FileEntry> files = eng.getRules();
			List<FileEntry> relatedFiles = new ArrayList<FileEntry>();
			List<FileEntry> relatedTestFiles = new ArrayList<FileEntry>();
			for(FileEntry f : files) {
				String intFileName = f.getFile();
				String fileName = (String)transMap.getKey(Integer.valueOf(f.getFile()));
				if(fileName.contains("Tests")) {
					if(!input.contains(fileName)) {
						f.setMessage(messageMap.get(f.getFile()));
						f.setFile(fileName);
						relatedTestFiles.add(f);
					}
				} else {
					if(!input.contains(fileName)) {
						f.setMessage(messageMap.get(f.getFile()));
						f.setFile(fileName);
						relatedFiles.add(f);
					}
				}
				System.out.println(transMap.getKey(Integer.valueOf(intFileName)));
			}
			inData.setRelatedProductFiles(relatedFiles);
			inData.setRelatedTestFiles(relatedTestFiles);
			
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
