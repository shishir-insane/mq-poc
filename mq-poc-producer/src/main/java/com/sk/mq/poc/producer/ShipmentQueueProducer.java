package com.sk.mq.poc.producer;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ShipmentQueueProducer {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue shipmentQueue;
	
	public void sendMessageToQueue(String message) {
		this.jmsMessagingTemplate.convertAndSend(shipmentQueue, message);
	}

}
