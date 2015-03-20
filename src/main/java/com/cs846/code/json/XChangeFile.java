/**
 * 
 */
package com.cs846.code.json;

import java.util.List;

/**
 * @author Atri
 *
 */
public class XChangeFile {
	
	String mode;
	
	List<String> changeSets;
	
	List<FileEntry> relatedProductFiles;
	
	String repository;
	
	String commitHash;
	
	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	List<FileEntry> relatedTestFiles;
	
	public List<String> getChangeSets() {
		return changeSets;
	}

	public void setChangeSets(List<String> changeSets) {
		this.changeSets = changeSets;
	}

	public List<FileEntry> getRelatedProductFiles() {
		return relatedProductFiles;
	}

	public void setRelatedProductFiles(List<FileEntry> relatedProductFiles) {
		this.relatedProductFiles = relatedProductFiles;
	}

	public List<FileEntry> getRelatedTestFiles() {
		return relatedTestFiles;
	}

	public void setRelatedTestFiles(List<FileEntry> relatedTestFiles) {
		this.relatedTestFiles = relatedTestFiles;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCommitHash() {
		return commitHash;
	}

	public void setCommitHash(String commitHash) {
		this.commitHash = commitHash;
	}


}
