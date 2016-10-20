
package com.jinmengzhu.groupdining.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * base bean
 *
 */
@MappedSuperclass
public abstract class BaseBean {

	protected Long id;

	protected Timestamp createTime;
	
	protected Timestamp lastUpdateTime;


	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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
