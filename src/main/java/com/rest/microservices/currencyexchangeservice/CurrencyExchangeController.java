package com.rest.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepository repository;

	@GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		if (exchangeValue == null) {
			throw new ExchangeValueNotFoundException("From: " + from + ", To: " + to);
		}
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("Inside /currency-exchange/from/{from}/to/{to} .retrieveExchangeValue.  Exchange Value: {}", exchangeValue );
		return exchangeValue;
	}
	
	@GetMapping(path = "/fault-tolerance/example")
	@HystrixCommand(fallbackMethod = "fallbackRetrieveExchangeValue")
	public ExchangeValue retrieveExchangeValue() {
		throw new RuntimeException("ERROR: RUNTIME EXCEPTION IN CurrencyEnchangeController: /fault-tolerance/example");
	}
	
	public ExchangeValue fallbackRetrieveExchangeValue() {
		return new ExchangeValue(-1, "USD", "INR", BigDecimal.valueOf(101));
	}
}
