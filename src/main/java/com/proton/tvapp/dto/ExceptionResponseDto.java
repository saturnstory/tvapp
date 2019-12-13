package com.proton.tvapp.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDto {
	private Date timestamp = new Date();
	
	private int status;
	private String error = "";
	private String type = "failure";
	private String message = "";
	
	private String path = "";
}
