/**
 * ShipmentController.java
 * mq-poc-prdocuer
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
package com.sk.db.poc.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sk.db.poc.producer.producer.ShipmentQueueProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shipment")
@Slf4j
public class ShipmentController {

	@Autowired
	private ShipmentQueueProducer shipmentQueueProducer;

	/**
	 * Post shipment message.
	 *
	 * @param message the message
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "text/plain")
	public void postShipmentMessage(@RequestBody String message) {
		log.debug("Received message: {}", message);
		if (StringUtils.isEmpty(message)) {
			throw new IllegalArgumentException("Input message should not be empty.");
		}
		shipmentQueueProducer.sendMessageToQueue(message);
	}
}
