import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;

import com.google.gson.Gson;


public class TestEgitAPI {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(GitHubConstants.OAUTH);
		
		CommitService service = new CommitService(client);
		IRepositoryIdProvider provider = RepositoryId.createFromId(GitHubConstants.REPOSITORY_PATH);
		List<RepositoryCommit> commitList = service.getCommits(provider,null,"src/main/java/org/elasticsearch/cluster/action/index/NodeIndexDeletedAction.java");
		Gson gson = new Gson();
		for(RepositoryCommit c : commitList){
			URL oracle = new URL(c.getUrl());
	        URLConnection yc = oracle.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                                    yc.getInputStream()));
	        com.cs846.code.json.RepositoryCommit rc = 
	        		gson.fromJson(in, com.cs846.code.json.RepositoryCommit.class);
	        System.out.println(rc.getSha());
	        List<com.cs846.code.json.Files> files = rc.getFiles();
	        for(com.cs846.code.json.Files f : files){
	        	System.out.println(f.getFilename());
	        }
	        
	        System.out.println();System.out.println();
	        
		}
		
		
		

	}

}
