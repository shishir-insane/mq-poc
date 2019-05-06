package com.sk.mq.poc.validation.impl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sk.mq.poc.dto.ShipmentMessage;
import com.sk.mq.poc.validation.MessageValidationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageParserValidationService implements MessageValidationService {

	private static final String REF_NUMBERS_FIELD_INDICATOR = ":20:";
	private static final String EXECUTION_DATE_FIELD_INDICATOR = ":32:";
	private static final String EXECUTION_DATE_FIELD_FORMAT = "YYMMDD";
	private static final String FX_RATE_FIELD_INDICATOR = ":36:";

	@Override
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
		if (StringUtils.startsWith(messageText, REF_NUMBERS_FIELD_INDICATOR)) {
			String fieldValueStr = messageText.replaceFirst(REF_NUMBERS_FIELD_INDICATOR, StringUtils.EMPTY);
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
		if (StringUtils.startsWith(messageText, EXECUTION_DATE_FIELD_INDICATOR)) {
			executionDate = messageText.replaceFirst(EXECUTION_DATE_FIELD_INDICATOR, StringUtils.EMPTY);
			if (StringUtils.isBlank(executionDate)) {
				throw new IllegalArgumentException("Input message does not contain execution date.");
			}
		} else {
			throw new IllegalArgumentException("Input message does not contain field for execution date.");
		}
		return executionDate;
	}

	private String parseMessageFxRate(String messageText) {
		log.debug("4th line of message: {}", messageText);
		String fxRate = null;
		if (StringUtils.startsWith(messageText, FX_RATE_FIELD_INDICATOR)) {
			fxRate = messageText.replaceFirst(fxRate, StringUtils.EMPTY);
		} else {
			throw new IllegalArgumentException("Input message does not contain field for execution date.");
		}
		return fxRate;
	}

}
