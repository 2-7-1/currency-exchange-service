package com.rest.microservices.currencyexchangeservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExchangeValueNotFoundException extends RuntimeException{

	public ExchangeValueNotFoundException(String arg0) {
		super(arg0);
	}
}
