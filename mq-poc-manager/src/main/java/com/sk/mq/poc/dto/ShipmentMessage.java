package com.sk.mq.poc.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShipmentMessage {
	private String requestType;
	private String trn;
	private String name;
	private String formatType;
	private String amount;
	private String currency;
	private String service;
	private String sourceCountryCode;
	private List<String> referenceNumbers;
	private String executionDate;
	private String fxRate;
	
}
