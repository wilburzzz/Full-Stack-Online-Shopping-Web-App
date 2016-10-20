package com.jinmengzhu.groupdining.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinmengzhu.groupdining.domain.BaseBeanUUID;

@Entity
@Table(name = "g_dish")
public class Dish extends BaseBeanUUID{
	
	private String name;
	
	private String description;
	
	private double price;
	
	private String pictrue;
	
	private String attribute;//非数据库字段
	
	private int amount;//非数据库字段
	
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "pictrue")
	public String getPictrue() {
		return pictrue;
	}

	public void setPictrue(String pictrue) {
		this.pictrue = pictrue;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
