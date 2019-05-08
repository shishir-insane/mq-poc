/**
 * ShipmentQueueConsumer.java
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
package com.sk.db.poc.consumer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sk.db.poc.consumer.delegate.ShipmentConsumerDelegate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipmentQueueConsumer {

	@Autowired
	private ShipmentConsumerDelegate shipmentConsumerDelegate;

	/**
	 * Receive queue.
	 *
	 * @param message the message
	 */
	@JmsListener(destination = "${app.config.queue.name}")
	public void receiveQueue(String message) {
		log.info("New Shipment Message Receieved: {}", message);
		if (StringUtils.isEmpty(message)) {
			throw new IllegalArgumentException("Empty message received from the queue.");
		}
		final String messageResult = shipmentConsumerDelegate.processConsumedMessage(message);
		log.info("Shipment Message Processed with Response: {}", messageResult);
	}

}
