package cok.sk.db.poc.validation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.sk.db.poc.validation.MqPocValidationManagerApplication;
import com.sk.db.poc.validation.dto.ShipmentMessage;
import com.sk.db.poc.validation.rest.ShipmentRestTemplateHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MqPocValidationManagerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ShipmentControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ShipmentRestTemplateHelper shipmentRestTemplateHelper;

	@Test
	public void whenPostValidMessage_thenReturnSuccess() throws Exception {
		when(shipmentRestTemplateHelper.postForShipmentMessage(any(ShipmentMessage.class), any(String.class)))
				.thenReturn("Got it. Thanks");
		MvcResult result = mockMvc
				.perform(post("/shipment").contentType(MediaType.APPLICATION_JSON)
						.content("GZXFRTJ675FTRHJJJ87zyxtBig Kumar           U000000000000017.450EURATCGB\r\n"
								+ ":20:TR234565,Zu8765Z,Bhj876t\r\n" + ":32A:180123\r\n" + ":36:12"))
				.andDo(print()).andExpect(status().is2xxSuccessful()).andReturn();
		assertThat(result.getResponse().getContentAsString(), containsString("Got it. Thanks"));

	}
}
