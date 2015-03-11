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
	
	List<String> relatedProductFiles;
	
	String repository;
	
	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	List<String> relatedTestFiles;
	
	public List<String> getChangeSets() {
		return changeSets;
	}

	public void setChangeSets(List<String> changeSets) {
		this.changeSets = changeSets;
	}

	public List<String> getRelatedProductFiles() {
		return relatedProductFiles;
	}

	public void setRelatedProductFiles(List<String> relatedProductFiles) {
		this.relatedProductFiles = relatedProductFiles;
	}

	public List<String> getRelatedTestFiles() {
		return relatedTestFiles;
	}

	public void setRelatedTestFiles(List<String> relatedTestFiles) {
		this.relatedTestFiles = relatedTestFiles;
	}


}
