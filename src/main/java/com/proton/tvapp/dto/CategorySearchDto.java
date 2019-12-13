package com.proton.tvapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategorySearchDto {	
	private String name;	
	private String seoLink;
	private String keywords;
	private String description;
	
	private String limit="10";
	private String page="1";
	
	private String nameSort;
	private String seoLinkSort;
}
