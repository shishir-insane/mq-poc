package com.sk.mq.poc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sk.mq.poc.service.ShipmentConsumerService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipmentQueueConsumer {
	
	@Autowired
	private ShipmentConsumerService shipmentConsumerService; 
	
	@JmsListener(destination = "${app.config.queue.name}")
	public void receiveQueue(String message) {
		log.info("New Shipment Message Receieved: {}", message);
		if (StringUtils.isEmpty(message)) {
			throw new IllegalArgumentException("Empty message received from the queue.");
		}
		shipmentConsumerService.processConsumedMessage(message);
	}

}
