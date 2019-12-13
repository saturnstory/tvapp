package com.proton.tvapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Category extends BaseEntity{
	private static final long serialVersionUID = 7015536278431165170L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "seo_link", unique = true)
	private String seoLink;
	
	@Column(name = "keywords")
	private String keywords;
	
	@Column(name = "description", length = 2000)
	private String description;
	
	
	
}
