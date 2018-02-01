package com.adroit.datacollector.vo;

import java.util.List;

public class ServiceConfiguration {

	private String exchangeName;

	private String apiType;

	private String productTarget;

	private List<InputParam> input;

	private String productSource;

	private String name;

	private String serviceType;

	private long timeToRefresh;

	private String url;

	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	public String getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(String productTarget) {
		this.productTarget = productTarget;
	}

	public List<InputParam> getInput() {
		return input;
	}

	public void setInput(List<InputParam> input) {
		this.input = input;
	}

	public String getProductSource() {
		return productSource;
	}

	public void setProductSource(String productSource) {
		this.productSource = productSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public long getTimeToRefresh() {
		return timeToRefresh;
	}

	public void setTimeToRefresh(long timeToRefresh) {
		this.timeToRefresh = timeToRefresh;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	@Override
	public String toString() {
		return "ServiceConfiguration [exchangeName=" + exchangeName + ", apiType=" + apiType + ", productTarget="
				+ productTarget + ", input=" + input + ", productSource=" + productSource + ", name=" + name
				+ ", serviceType=" + serviceType + ", timeToRefresh=" + timeToRefresh + ", url=" + url + "]";
	}

}
