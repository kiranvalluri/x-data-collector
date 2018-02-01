package com.adroit.datacollector.vo;

public class TickerMessageEnvelope {

	private String message;
	private String exchangeName;
	private String sourceProduct;
	private String targetProduct;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getSourceProduct() {
		return sourceProduct;
	}

	public void setSourceProduct(String sourceProduct) {
		this.sourceProduct = sourceProduct;
	}

	public String getTargetProduct() {
		return targetProduct;
	}

	public void setTargetProduct(String targetProduct) {
		this.targetProduct = targetProduct;
	}

	@Override
	public String toString() {
		return "TickerMessageEnvelope [message=" + message + ", exchangeName=" + exchangeName + ", sourceProduct="
				+ sourceProduct + ", targetProduct=" + targetProduct + "]";
	}

}
