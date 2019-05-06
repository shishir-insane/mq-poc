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
	
	@Bean
	public Queue shipmentQueue() {
		log.info("Connection to queue {} is initilized in the application.", queueName);
		return new ActiveMQQueue(queueName);
	}
}
