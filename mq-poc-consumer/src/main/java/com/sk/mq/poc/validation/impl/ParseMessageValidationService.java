/**
 * ParseMessageValidationService.java
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
package com.sk.mq.poc.validation.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sk.mq.poc.dto.ShipmentMessage;
import com.sk.mq.poc.validation.MessageValidationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParseMessageValidationService implements MessageValidationService {

	@Value("${app.config.fields.ref-number}")
	private String refNumberFieldIndicator;

	@Value("${app.config.fields.execution-date}")
	private String executionDateFieldIndicator;

	@Value("${app.config.fields.fx-rate}")
	private String fxRateFieldIndicator;

	@Value("${app.config.validation.execution-date-format}")
	private String executionDateFieldFormat;

	@Value("${app.config.validation.request-type-value}")
	private String requestTypeFixedValue;

	@Value("${app.config.validation.fields-pattern}")
	private String firstLineFieldsPattern;

	@Value("${app.config.validation.format-type-value}")
	private String formatTypeFixedValue;

	@Value("${app.config.validation.amount-pattern}")
	private String amountFormatPattern;

	@Value("${app.config.suspicious.condition.execution-date-text}")
	private String suspiciousExecutionDateText;

	/**
	 * Validate received message.
	 *
	 * @param messageText the message text
	 * @return the shipment message
	 */
	public ShipmentMessage validateReceivedMessage(String messageText) {
		return parseInputMessageFields(messageText);
	}

	/**
	 * Parses the input message fields.
	 *
	 * @param message the message
	 * @return the shipment message
	 */
	private ShipmentMessage parseInputMessageFields(String message) {
		final List<String> splitByLineStrings = Arrays.asList(message.split("\\r?\\n"));
		ShipmentMessage shipmentMessage = null;
		if (CollectionUtils.isEmpty(splitByLineStrings) || splitByLineStrings.size() < 4) {
			throw new IllegalArgumentException("Input message does not contain all required fields.");
		}
		shipmentMessage = parseMessageFieldsOnFirstLine(splitByLineStrings.get(0), shipmentMessage);
		shipmentMessage.setReferenceNumbers(parseMessageReferenceNumber(splitByLineStrings.get(1)));
		shipmentMessage.setExecutionDate(parseMessageExecutionDate(splitByLineStrings.get(2)));
		shipmentMessage.setFxRate(parseMessageFxRate(splitByLineStrings.get(3)));
		log.info("Parsed Input Message: {}", shipmentMessage);
		return shipmentMessage;
	}

	/**
	 * Parses the message fields on first line.
	 *
	 * @param messageText the message text
	 * @param message     the message
	 * @return the shipment message
	 */
	private ShipmentMessage parseMessageFieldsOnFirstLine(String messageText, ShipmentMessage message) {
		log.debug("1st line of message: {}", messageText);
		final Pattern pattern = Pattern.compile(firstLineFieldsPattern);
		final Matcher matcher = pattern.matcher(messageText);
		if (matcher.matches()) {
			log.debug("Matched text groups: {}", matcher.groupCount());
			log.debug("Matched text: {}", matcher.group(0));
			int counter = 1;
			message = new ShipmentMessage();

			// Check for fixed value for request type
			if (!requestTypeFixedValue.equalsIgnoreCase(matcher.group(counter))) {
				throw new IllegalArgumentException(
						"Input message does not contain standard request type on the first line.");
			}
			message.setRequestType(matcher.group(counter++));
			message.setTrn(matcher.group(counter++));
			message.setName(matcher.group(counter++));

			// Check for fixed value for format type
			if (!formatTypeFixedValue.equalsIgnoreCase(matcher.group(counter))) {
				throw new IllegalArgumentException(
						"Input message does not contain standard format type on the first line.");
			}
			message.setFormatType(matcher.group(counter++));

			// Check for pattern value for amount
			final Pattern amountPattern = Pattern.compile(amountFormatPattern);
			final Matcher amountMatcher = amountPattern.matcher(matcher.group(counter));
			if (!amountMatcher.find()) {
				throw new IllegalArgumentException(
						"Input message does not contain amount standard required format on the first line.");
			}

			message.setAmount(matcher.group(counter++));
			message.setCurrency(matcher.group(counter++));
			message.setService(matcher.group(counter++));
			message.setSourceCountryCode(matcher.group(counter++));
			log.debug("DTO after setting 1st line text: {}", message);
		} else {
			throw new IllegalArgumentException(
					"Input message does not contain all standard required fields on the first line.");
		}
		return message;
	}

	/**
	 * Parses the message reference number.
	 *
	 * @param messageText the message text
	 * @return the list
	 */
	private List<String> parseMessageReferenceNumber(String messageText) {
		log.debug("2nd line of message: {}", messageText);
		List<String> referenceNumbers = null;
		if (StringUtils.startsWith(messageText, refNumberFieldIndicator)) {
			final String fieldValueStr = messageText.replaceFirst(refNumberFieldIndicator, StringUtils.EMPTY);
			if (StringUtils.isBlank(fieldValueStr)) {
				throw new IllegalArgumentException("Input message does not contain reference numbers.");
			}
			referenceNumbers = Arrays.asList(StringUtils.split(fieldValueStr, ","));
			log.debug("List of referenceNumbers after parsing 2nd line text: {}", referenceNumbers);
		} else {
			throw new IllegalArgumentException("Input message does not contain field for reference numbers.");
		}
		return referenceNumbers;
	}

	/**
	 * Parses the message execution date.
	 *
	 * @param messageText the message text
	 * @return the string
	 */
	private String parseMessageExecutionDate(String messageText) {
		log.debug("3rd line of message: {}", messageText);
		String executionDate = null;
		if (StringUtils.startsWith(messageText, executionDateFieldIndicator)) {
			executionDate = messageText.replaceFirst(executionDateFieldIndicator, StringUtils.EMPTY);
			if (StringUtils.isBlank(executionDate)) {
				throw new IllegalArgumentException("Input message does not contain execution date.");
			}
			if (!StringUtils.containsIgnoreCase(executionDate, suspiciousExecutionDateText)) {
				if (!isValidDateFormat(executionDate)) {
					throw new IllegalArgumentException(
							"Input message does not contain valid execution date in standard required format.");
				}
			}
		} else {
			throw new IllegalArgumentException("Input message does not contain field for execution date.");
		}
		log.debug("Execution Date text after parsing 3rd line text: {}", executionDate);
		return executionDate;
	}

	/**
	 * Parses the message fx rate.
	 *
	 * @param messageText the message text
	 * @return the string
	 */
	private String parseMessageFxRate(String messageText) {
		log.debug("4th line of message: {}", messageText);
		String fxRate = null;
		if (StringUtils.startsWith(messageText, fxRateFieldIndicator)) {
			fxRate = messageText.replaceFirst(fxRateFieldIndicator, StringUtils.EMPTY);
		} else {
			throw new IllegalArgumentException("Input message does not contain field for FX rate.");
		}
		log.debug("FX Rate text after parsing 3rd line text: {}", fxRate);
		return fxRate;
	}

	/**
	 * Checks if is valid date format.
	 *
	 * @param dateStr the date str
	 * @return true, if is valid date format
	 */
	private boolean isValidDateFormat(String dateStr) {
		Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(executionDateFieldFormat);
            date = sdf.parse(dateStr);
            if (!dateStr.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            return false;
        }
        return date != null;
	}

}
