package cok.sk.db.poc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sk.db.poc.shipment.MqPocManagerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MqPocManagerApplication.class)
public class MqPocManagerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
