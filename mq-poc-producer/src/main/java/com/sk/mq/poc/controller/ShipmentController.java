package com.sk.mq.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sk.mq.poc.producer.ShipmentQueueProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shipment")
@Slf4j
public class ShipmentController {

	@Autowired
	private ShipmentQueueProducer shipmentQueueProducer;

	@RequestMapping(method = RequestMethod.POST, consumes = "text/plain")
	public void getShipmentMessage(@RequestBody String message) {
		log.debug("Received message: {}", message);
		if (StringUtils.isEmpty(message)) {
			throw new IllegalArgumentException("Input message should not be empty.");
		}
		shipmentQueueProducer.sendMessageToQueue(message);
	}
}
