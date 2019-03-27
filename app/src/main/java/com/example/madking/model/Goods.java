package com.example.madking.model;

import java.io.Serializable;

/**
 * ��Ʒ
 * @author 702-a01
 *
 */
public class Goods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	
	private String name;
	
	private String picture; //ͼƬ
	
	private Integer typeid;  //�����ķ�������
	
	private Integer merchantid;  //�����̼�
	
	private Double price;  //�۸�

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

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(Integer merchantid) {
		this.merchantid = merchantid;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", picture=" + picture + ", typeid=" + typeid + ", merchantid="
				+ merchantid + ", price=" + price + "]";
	}

}
