package com.sk.mq.poc.validation.impl;

import java.util.Arrays;
import java.util.List;
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

	public ShipmentMessage validateReceivedMessage(String messageText) {
		return parseInputMessageFields(messageText);
	}

	private ShipmentMessage parseInputMessageFields(String message) {
		List<String> splitByLineStrings = Arrays.asList(message.split("\\r?\\n"));
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

	private ShipmentMessage parseMessageFieldsOnFirstLine(String messageText, ShipmentMessage message) {
		log.debug("1st line of message: {}", messageText);
		Pattern pattern = Pattern.compile("^(.{1})(.{22})(.{20})(.{1})(.{19})(.{3})(.{3})(.{2}).*");
		Matcher matcher = pattern.matcher(messageText);
		if (matcher.matches()) {
			log.debug("Matched text groups: {}", matcher.groupCount());
			log.debug("Matched text: {}", matcher.group(0));
			int counter = 1;
			message = new ShipmentMessage();
			message.setRequestType(matcher.group(counter++));
			message.setTrn(matcher.group(counter++));
			message.setName(matcher.group(counter++));
			message.setFormatType(matcher.group(counter++));
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

	private List<String> parseMessageReferenceNumber(String messageText) {
		log.debug("2nd line of message: {}", messageText);
		List<String> referenceNumbers = null;
		if (StringUtils.startsWith(messageText, refNumberFieldIndicator)) {
			String fieldValueStr = messageText.replaceFirst(refNumberFieldIndicator, StringUtils.EMPTY);
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

	private String parseMessageExecutionDate(String messageText) {
		log.debug("3rd line of message: {}", messageText);
		String executionDate = null;
		if (StringUtils.startsWith(messageText, executionDateFieldIndicator)) {
			executionDate = messageText.replaceFirst(executionDateFieldIndicator, StringUtils.EMPTY);
			if (StringUtils.isBlank(executionDate)) {
				throw new IllegalArgumentException("Input message does not contain execution date.");
			}
		} else {
			throw new IllegalArgumentException("Input message does not contain field for execution date.");
		}
		log.debug("Execution Date text after parsing 3rd line text: {}", executionDate);
		return executionDate;
	}

	private String parseMessageFxRate(String messageText) {
		log.debug("4th line of message: {}", messageText);
		String fxRate = null;
		if (StringUtils.startsWith(messageText, fxRateFieldIndicator)) {
			fxRate = messageText.replaceFirst(fxRateFieldIndicator, StringUtils.EMPTY);
		} else {
			throw new IllegalArgumentException("Input message does not contain field for execution date.");
		}
		log.debug("FX Rate text after parsing 3rd line text: {}", fxRate);
		return fxRate;
	}

}
