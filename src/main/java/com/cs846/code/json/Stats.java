
package com.cs846.code.json;

import java.util.List;

public class Stats{
   	private Number additions;
   	private Number deletions;
   	private Number total;

 	public Number getAdditions(){
		return this.additions;
	}
	public void setAdditions(Number additions){
		this.additions = additions;
	}
 	public Number getDeletions(){
		return this.deletions;
	}
	public void setDeletions(Number deletions){
		this.deletions = deletions;
	}
 	public Number getTotal(){
		return this.total;
	}
	public void setTotal(Number total){
		this.total = total;
	}
}
