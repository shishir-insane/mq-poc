/**
 * DefaultShipmentConsumerDelegate.java
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
package com.sk.db.poc.consumer.delegate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.db.poc.consumer.delegate.ShipmentConsumerDelegate;
import com.sk.db.poc.consumer.rest.ShipmentValidationRestTemplateHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultShipmentConsumerDelegate implements ShipmentConsumerDelegate {

	@Autowired
	private ShipmentValidationRestTemplateHelper restTemplateHelper;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.sk.db.poc.consumer.delegate.ShipmentConsumerDelegate#processConsumedMessage(java.
	 * lang.String)
	 */
	@Override
	public String processConsumedMessage(String messageText) {
		final String messageResult = restTemplateHelper.postForShipmentMessage(messageText);
		log.debug("Message posted with response: {}", messageResult);
		return messageResult;
	}

}
