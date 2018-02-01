package com.adroit.datacollector.vo;

import java.io.Serializable;
import java.util.List;

public class ExchangeConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2890913838852991236L;
	private String exchangeName;

	private List<ServiceConfiguration> service;

	@Override
	public String toString() {
		return "ExchangeConfig [exchnageName=" + exchangeName + ", service=" + service + "]";
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public List<ServiceConfiguration> getService() {
		return service;
	}

	public void setService(List<ServiceConfiguration> service) {
		this.service = service;
	}

}
