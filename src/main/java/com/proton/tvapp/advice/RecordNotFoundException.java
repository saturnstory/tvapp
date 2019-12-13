package com.proton.tvapp.advice;

public class RecordNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -240814881724750497L;

	public RecordNotFoundException(String message) {
		super(message);
	}
}
