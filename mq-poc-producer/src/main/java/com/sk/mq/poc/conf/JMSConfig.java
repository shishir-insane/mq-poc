/**
 * JMSConfig.java
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
package com.sk.mq.poc.conf;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableJms
@Slf4j
public class JMSConfig {

	@Value("${app.config.queue.name}")
	private String queueName;

	/**
	 * Shipment queue.
	 *
	 * @return the queue
	 */
	@Bean
	public Queue shipmentQueue() {
		log.info("Connection to queue {} is initilized in the application.", queueName);
		return new ActiveMQQueue(queueName);
	}
}
