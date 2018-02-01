package com.adroit.datacollector.vo;

import java.util.List;

public class ExchangeConfiguration {
	private List<ExchangeConfig> exchange;

	public List<ExchangeConfig> getExchange() {
		return exchange;
	}

	public void setExchange(List<ExchangeConfig> exchange) {
		this.exchange = exchange;
	}

	@Override
	public String toString() {
		return "ExchangeConfiguration [exchange=" + exchange + "]";
	}

}