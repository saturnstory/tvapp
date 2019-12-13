package com.proton.tvapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable{
	private static final long serialVersionUID = -6543494261049643467L;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="created_by", columnDefinition = "bigint(20) default 0")
	private long createdBy;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@Column(name="updated_by", columnDefinition = "bigint(20) default 0")
	private long updatedBy;
	
	@Column(name="status")
	private boolean status;
	
	
	

}
