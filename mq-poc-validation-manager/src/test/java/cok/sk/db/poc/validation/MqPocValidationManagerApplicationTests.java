package cok.sk.db.poc.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sk.db.poc.validation.MqPocValidationManagerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MqPocValidationManagerApplication.class)
@ActiveProfiles("test")
public class MqPocValidationManagerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
