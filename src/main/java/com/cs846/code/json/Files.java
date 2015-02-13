
package com.cs846.code.json;

import java.util.List;

public class Files{
   	private Number additions;
   	private String blob_url;
   	private Number changes;
   	private String contents_url;
   	private Number deletions;
   	private String filename;
   	private String patch;
   	private String raw_url;
   	private String sha;
   	private String status;

 	public Number getAdditions(){
		return this.additions;
	}
	public void setAdditions(Number additions){
		this.additions = additions;
	}
 	public String getBlob_url(){
		return this.blob_url;
	}
	public void setBlob_url(String blob_url){
		this.blob_url = blob_url;
	}
 	public Number getChanges(){
		return this.changes;
	}
	public void setChanges(Number changes){
		this.changes = changes;
	}
 	public String getContents_url(){
		return this.contents_url;
	}
	public void setContents_url(String contents_url){
		this.contents_url = contents_url;
	}
 	public Number getDeletions(){
		return this.deletions;
	}
	public void setDeletions(Number deletions){
		this.deletions = deletions;
	}
 	public String getFilename(){
		return this.filename;
	}
	public void setFilename(String filename){
		this.filename = filename;
	}
 	public String getPatch(){
		return this.patch;
	}
	public void setPatch(String patch){
		this.patch = patch;
	}
 	public String getRaw_url(){
		return this.raw_url;
	}
	public void setRaw_url(String raw_url){
		this.raw_url = raw_url;
	}
 	public String getSha(){
		return this.sha;
	}
	public void setSha(String sha){
		this.sha = sha;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
