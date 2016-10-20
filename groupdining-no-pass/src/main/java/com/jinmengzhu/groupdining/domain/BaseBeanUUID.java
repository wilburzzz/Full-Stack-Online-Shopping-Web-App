
package com.jinmengzhu.groupdining.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;


/**
 * base bean
 */
@MappedSuperclass
public abstract class BaseBeanUUID {

	protected String id;//编号 

	protected Timestamp createTime;//创建时间
	
	protected Timestamp lastUpdateTime;//修改时间
	
	@Id
	@GeneratedValue(generator = "_uuid")
	@GenericGenerator(name="_uuid",strategy="uuid")
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "create_time", length = 0)	
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_update_time", length = 0)
	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
