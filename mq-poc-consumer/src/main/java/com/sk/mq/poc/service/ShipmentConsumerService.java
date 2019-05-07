/**
 * ShipmentConsumerService.java
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
package com.sk.mq.poc.service;

public interface ShipmentConsumerService {

	/**
	 * Process consumed message.
	 *
	 * @param message the message
	 */
	void processConsumedMessage(String message);
}
