package com.book.response;

/**
 * 
 * @author cogjava3180
 * MessageResponse bean is used for declaring response details
 *
 */

public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
	    this.message = message;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
