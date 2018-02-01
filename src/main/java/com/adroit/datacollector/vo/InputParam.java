package com.adroit.datacollector.vo;

public class InputParam {

	private String paramValue;

	private String paramName;

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Override
	public String toString() {
		return "InputParam [paramValue=" + paramValue + ", paramName=" + paramName + "]";
	}

}
