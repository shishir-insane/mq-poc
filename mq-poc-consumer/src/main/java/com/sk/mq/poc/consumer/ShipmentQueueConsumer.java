package com.sk.mq.poc.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipmentQueueConsumer {
	
	@JmsListener(destination = "${app.config.queue.name}")
	public void receiveQueue(String message) {
		log.info("New Shipment Message Receieved: ", message);
	}

}
