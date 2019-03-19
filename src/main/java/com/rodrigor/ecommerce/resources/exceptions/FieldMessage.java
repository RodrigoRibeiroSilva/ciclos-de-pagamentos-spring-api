package com.rodrigor.ecommerce.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fiedName, message;
	
	public FieldMessage() {
		
	}

	public FieldMessage(String fiedName, String message) {
		super();
		this.fiedName = fiedName;
		this.message = message;
	}

	public String getFiedName() {
		return fiedName;
	}

	public void setFiedName(String fiedName) {
		this.fiedName = fiedName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FieldMessage [fiedName=" + fiedName + ", message=" + message + "]";
	}
	
}
