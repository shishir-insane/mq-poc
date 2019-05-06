package com.sk.mq.poc.validation;

import com.sk.mq.poc.dto.ShipmentMessage;

public interface MessageValidationService {

	ShipmentMessage validateReceivedMessage(String messageText);
}
