
package com.cs846.code.json;

import java.util.List;

public class RepositoryCommit{
   	private Author author;
   	private String comments_url;
   	private Commit commit;
   	private Committer committer;
   	private List<Files> files;
   	private String html_url;
   	private List<Parents> parents;
   	private String sha;
   	private Stats stats;
   	private String url;

 	public Author getAuthor(){
		return this.author;
	}
	public void setAuthor(Author author){
		this.author = author;
	}
 	public String getComments_url(){
		return this.comments_url;
	}
	public void setComments_url(String comments_url){
		this.comments_url = comments_url;
	}
 	public Commit getCommit(){
		return this.commit;
	}
	public void setCommit(Commit commit){
		this.commit = commit;
	}
 	public Committer getCommitter(){
		return this.committer;
	}
	public void setCommitter(Committer committer){
		this.committer = committer;
	}
 	public List<Files> getFiles(){
		return this.files;
	}
	public void setFiles(List<Files> files){
		this.files = files;
	}
 	public String getHtml_url(){
		return this.html_url;
	}
	public void setHtml_url(String html_url){
		this.html_url = html_url;
	}
 	public List<Parents> getParents(){
		return this.parents;
	}
	public void setParents(List<Parents> parents){
		this.parents = parents;
	}
 	public String getSha(){
		return this.sha;
	}
	public void setSha(String sha){
		this.sha = sha;
	}
 	public Stats getStats(){
		return this.stats;
	}
	public void setStats(Stats stats){
		this.stats = stats;
	}
 	public String getUrl(){
		return this.url;
	}
	public void setUrl(String url){
		this.url = url;
	}
}
