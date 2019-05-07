package cok.sk.db.poc.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sk.mq.poc.dto.ShipmentMessage;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shipment")
@Slf4j
public class ShipmentController {
	
	@RequestMapping(method = RequestMethod.POST)
	public String postNewShipment(@RequestBody ShipmentMessage message) {
		log.info("New Shipment Message Recieved: {}", message);
		return "Got it. Thanks";
	}
		

}
