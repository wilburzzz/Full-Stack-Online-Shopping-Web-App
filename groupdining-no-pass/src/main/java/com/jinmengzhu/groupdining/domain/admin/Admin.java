package com.jinmengzhu.groupdining.domain.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinmengzhu.groupdining.domain.BaseBeanUUID;

@Entity
@Table(name = "g_admin")
public class Admin extends BaseBeanUUID{
	
	private String name;//用户名、登陆名
	
	private String email;//邮件
	
	private String realName;//真实姓名
	
	private String password;//密码

	private String sex;//性别
	
	private String remark;
	
	private String style;

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "real_name")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "style")
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
}
