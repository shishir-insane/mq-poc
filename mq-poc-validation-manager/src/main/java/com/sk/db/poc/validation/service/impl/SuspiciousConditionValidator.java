/**
 * SuspiciousConditionValidator.java
 * mq-poc-validation-manager
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
package com.sk.db.poc.validation.service.impl;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sk.db.poc.validation.dto.ShipmentMessage;
import com.sk.db.poc.validation.service.MessageValidator;

@Service
public class SuspiciousConditionValidator implements MessageValidator {

	@Value("${app.config.suspicious.condition.names}")
	private String[] suspiciousNames;

	@Value("${app.config.suspicious.condition.source-country}")
	private String suspiciousSourceCountry;

	@Value("${app.config.suspicious.condition.service-code}")
	private String suspiciousServiceCode;

	@Value("${app.config.suspicious.condition.execution-date-text}")
	private String suspiciousExecutionDateText;

	/**
	 * Checks if is in valid received message.
	 *
	 * @param message the message
	 * @return true, if is in valid received message
	 */
	public boolean isInValidReceivedMessage(ShipmentMessage message) {
		return isInValidName(message.getName().trim()) && isInValidSourceCountry(message.getSourceCountryCode())
				&& isInValidServiceCode(message.getService()) && isInValidExecutionDateText(message.getExecutionDate());
	}

	/**
	 * Checks if is in valid name.
	 *
	 * @param name the name
	 * @return true, if is in valid name
	 */
	private boolean isInValidName(String name) {
		return Arrays.stream(suspiciousNames).anyMatch(name::equalsIgnoreCase);
	}

	/**
	 * Checks if is in valid source country.
	 *
	 * @param sourceCountry the source country
	 * @return true, if is in valid source country
	 */
	private boolean isInValidSourceCountry(String sourceCountry) {
		return suspiciousSourceCountry.equalsIgnoreCase(sourceCountry);
	}

	/**
	 * Checks if is in valid service code.
	 *
	 * @param serviceCode the service code
	 * @return true, if is in valid service code
	 */
	private boolean isInValidServiceCode(String serviceCode) {
		return suspiciousServiceCode.equalsIgnoreCase(serviceCode);
	}

	/**
	 * Checks if is in valid execution date text.
	 *
	 * @param executionDateText the execution date text
	 * @return true, if is in valid execution date text
	 */
	private boolean isInValidExecutionDateText(String executionDateText) {
		return StringUtils.containsIgnoreCase(executionDateText, suspiciousExecutionDateText);
	}
}
