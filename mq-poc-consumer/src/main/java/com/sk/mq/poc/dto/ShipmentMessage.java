/**
 * ShipmentMessage.java
 * mq-poc-consumer
 * Copyright 2019 Shishir Kumar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
package com.sk.mq.poc.dto;

import java.util.List;

import lombok.Data;

/**
 * Instantiates a new shipment message.
 */
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
