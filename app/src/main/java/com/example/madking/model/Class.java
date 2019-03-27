package com.example.madking.model;

import java.io.Serializable;

public class Class implements Serializable{
	
	/**
	 * 
	 */
	private Integer id;
	private String picture;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
