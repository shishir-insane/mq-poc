/**
 * ShipmentController.java
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
package com.sk.db.poc.validation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sk.db.poc.validation.service.ShipmentValidationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shipment")
@Slf4j
public class ShipmentController {

	@Autowired
	private ShipmentValidationService shipmentValidationService;

	/**
	 * Post new shipment.
	 *
	 * @param message the message
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String postNewShipment(@RequestBody String message) {
		log.info("New Shipment Message Recieved: {}", message);
		return shipmentValidationService.processConsumedMessage(message);
	}

}
