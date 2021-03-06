package com.rest.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Integer>{

	public ExchangeValue findByFromAndTo(String from, String to);
}
