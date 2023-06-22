package com.example.todofamilyapi.controller.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
	private String message;

	public MessageResponse(final String message) {
	    this.message = message;
	  }
}
