package com.proton.tvapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "channel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Channel extends BaseEntity {
	private static final long serialVersionUID = 5146022112494878255L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "logo_url")
	private String logoUrl;
	
	@Column(name= "type")
	private int type;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	@OneToMany(mappedBy = "channel")
	private List<ChannelContent> contents = new ArrayList<ChannelContent>();
	
	@Column(name = "about", length = 2000)
	private String about;
	
	@Column(name = "seo_link", unique = true)
	private String seoLink;
	
	@Column(name = "description", length = 2000)
	private String description;
	
	@Column(name = "keywords")
	private String keywords;

}
