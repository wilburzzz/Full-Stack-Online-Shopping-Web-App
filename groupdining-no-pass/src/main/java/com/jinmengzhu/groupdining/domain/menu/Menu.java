package com.jinmengzhu.groupdining.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinmengzhu.groupdining.domain.BaseBeanUUID;

@Entity
@Table(name = "g_menu")
public class Menu extends BaseBeanUUID{

	private String name;
	
	private String style;
	
	private String description;
	
	private double price;
	
	private double discount;
	
	private String pictrue;
	
	private String dishIds;//dishId,dishId,dishId

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "style")
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	@Column(name = "discount")
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Column(name = "pictrue")
	public String getPictrue() {
		return pictrue;
	}

	public void setPictrue(String pictrue) {
		this.pictrue = pictrue;
	}

	@Column(name = "dish_id")
	public String getDishIds() {
		return dishIds;
	}

	public void setDishIds(String dishIds) {
		this.dishIds = dishIds;
	}
}
