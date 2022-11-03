package ru.SensorApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.SensorApp.models.Sensor;
import ru.SensorApp.services.SensorService;

@SpringBootTest
class FirstRestAppApplicationTests {
	private Sensor sensor;
	@MockBean
	private final SensorService sensorService;

	public FirstRestAppApplicationTests(SensorService sensorService) {
		this.sensorService = sensorService;
	}

	@BeforeEach
	public void setUp(){
		sensor = new Sensor("TestSensor");
	}

	@Test
	void contextLoads() {



	}

}
