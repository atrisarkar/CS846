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
	
	List<String> changeSets;
	
	List<FileEntry> relatedProductFiles;
	
	String repository;
	
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


}
