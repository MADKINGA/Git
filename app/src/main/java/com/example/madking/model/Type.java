package com.example.madking.model;

import java.io.Serializable;

public class Type implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String name;
	
	private String picture;
	
	
	public Type(){}

	public Type(Integer id, String name, String picture) {
		super();
		this.id = id;
		this.name = name;
		this.picture = picture;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + ", picture=" + picture
				+ "]";
	}
	
	

}
