package com.proton.tvapp.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponseDto {
	private Date timestamp = new Date();
	
	private int status;
	private String error = "";
	
	private String code = "";
	private String type = "success";
	private String message = "";
	private Object data;
}
