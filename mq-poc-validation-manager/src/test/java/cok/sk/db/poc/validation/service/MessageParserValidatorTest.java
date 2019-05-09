package cok.sk.db.poc.validation.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sk.db.poc.validation.MqPocValidationManagerApplication;
import com.sk.db.poc.validation.dto.ShipmentMessage;
import com.sk.db.poc.validation.service.impl.MessageParserValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MqPocValidationManagerApplication.class)
@ActiveProfiles("test")
public class MessageParserValidatorTest {

	@Autowired
	private MessageParserValidator validator;
	
	private String messageText;
	
	private String invalidDateMessageText;
	
	private String suspiciousMessage;
	
	@Before
	public void init() {
		messageText = "GZXFRTJ675FTRHJJJ87zyxtBig Kumar           U000000000000017.450EURATCGB\r\n" + 
				":20:TR234565,Zu8765Z,Bhj876t\r\n" + 
				":32A:180123\r\n" + 
				":36:12";
		
		invalidDateMessageText = "GZXFRTJ675FTRHJJJ87zyxtBig Kumar           U000000000000017.450EURATCGB\r\n" + 
				":20:TR234565,Zu8765Z,Bhj876t\r\n" + 
				":32A:1801231\r\n" + 
				":36:12";
		
		suspiciousMessage = "GZXFRTJ675FTRHJJJ87zyxtChang Imagine       U000000000000017.450EURATZAT\r\n" + 
				":20:TR234565,Zu8765Z,Bhj876t\r\n" + 
				":32A:180123Ship dual Fert chem\r\n" + 
				":36:12";
	}

	@Test
	public void validateReceivedMessage_validMessage_thenVerifyName() {
		ShipmentMessage message = validator.validateReceivedMessage(messageText);
		assertNotNull(message);
		assertEquals("Big Kumar           ", message.getName());
	}
	
	@Test
	public void validateReceivedMessage_validMessage_thenVerifyDate() {
		ShipmentMessage message = validator.validateReceivedMessage(messageText);
		assertNotNull(message);
		assertEquals("180123", message.getExecutionDate());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void validateReceivedMessage_invalidDateMessage_thenVerifyDate() {
		validator.validateReceivedMessage(invalidDateMessageText);
	}
}
