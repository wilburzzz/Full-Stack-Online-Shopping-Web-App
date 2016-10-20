package com.jinmengzhu.groupdining.domain.cart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinmengzhu.groupdining.domain.BaseBeanUUID;

@Entity
@Table(name = "g_cart")
public class Cart extends BaseBeanUUID{

	private String menuId;//菜单id

	private String userId;//用户id

	@Column(name = "menu_id")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
