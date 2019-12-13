package com.proton.tvapp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonRootName("category")
public class CategoryDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	
	@NotEmpty
	@Size(max=100)
	private String name;
	
	@NotEmpty
	private String seoLink;
	
	@Size(max=255)
	private String keywords;
	
	@Size(max=200)
	private String description;
}
