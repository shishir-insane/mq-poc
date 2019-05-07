package com.sk.mq.poc.validation.impl;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sk.mq.poc.dto.ShipmentMessage;
import com.sk.mq.poc.validation.MessageValidationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SuspiciousConditionValidationService implements MessageValidationService {

	@Value("${app.config.suspicious.condition.names}")
	private String[] suspiciousNames;

	@Value("${app.config.fields.execution-date-format}")
	private String executionDateFieldFormat;

	@Value("${app.config.suspicious.condition.source-country}")
	private String suspiciousSourceCountry;

	@Value("${app.config.suspicious.condition.service-code}")
	private String suspiciousServiceCode;

	@Value("${app.config.suspicious.condition.execution-date-text}")
	private String suspiciousExecutionDateText;

	public boolean isInValidReceivedMessage(ShipmentMessage message) {
		return isInValidName(message.getName().trim()) && isInValidSourceCountry(message.getSourceCountryCode())
				&& isInValidServiceCode(message.getService()) && isInValidExecutionDateText(message.getExecutionDate());
	}

	private boolean isInValidName(String name) {
		return Arrays.stream(suspiciousNames).anyMatch(name::equalsIgnoreCase);
	}

	private boolean isInValidSourceCountry(String sourceCountry) {
		return suspiciousSourceCountry.equalsIgnoreCase(sourceCountry);
	}

	private boolean isInValidServiceCode(String serviceCode) {
		return suspiciousServiceCode.equalsIgnoreCase(serviceCode);
	}

	private boolean isInValidExecutionDateText(String executionDateText) {
		return StringUtils.containsIgnoreCase(executionDateText, suspiciousExecutionDateText);
	}
}
