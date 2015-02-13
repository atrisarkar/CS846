
package com.cs846.code.json;

import java.util.List;

public class Commit{
   	private Author author;
   	private Number comment_count;
   	private Committer committer;
   	private String message;
   	private Tree tree;
   	private String url;

 	public Author getAuthor(){
		return this.author;
	}
	public void setAuthor(Author author){
		this.author = author;
	}
 	public Number getComment_count(){
		return this.comment_count;
	}
	public void setComment_count(Number comment_count){
		this.comment_count = comment_count;
	}
 	public Committer getCommitter(){
		return this.committer;
	}
	public void setCommitter(Committer committer){
		this.committer = committer;
	}
 	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}
 	public Tree getTree(){
		return this.tree;
	}
	public void setTree(Tree tree){
		this.tree = tree;
	}
 	public String getUrl(){
		return this.url;
	}
	public void setUrl(String url){
		this.url = url;
	}
}
