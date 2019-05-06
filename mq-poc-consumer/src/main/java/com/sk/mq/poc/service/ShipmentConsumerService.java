package com.sk.mq.poc.service;

public interface ShipmentConsumerService {
	
	void processConsumedMessage(String message);
}
