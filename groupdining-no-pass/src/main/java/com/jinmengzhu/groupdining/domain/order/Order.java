package com.jinmengzhu.groupdining.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinmengzhu.groupdining.domain.BaseBeanUUID;

@Entity
@Table(name = "g_order")
public class Order extends BaseBeanUUID{
	
	private String description;
		
	private double price;

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
}
