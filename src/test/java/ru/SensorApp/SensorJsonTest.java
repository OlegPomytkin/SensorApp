package ru.SensorApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.SensorApp.dto.SensorDTO;
import ru.SensorApp.models.Sensor;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class SensorJsonTest {

    @Autowired
    private JacksonTester<Sensor> json;

    @Autowired
    private JacksonTester<SensorDTO> sensorDTOJacksonTester;


    @Test
    public void testSerialize() throws Exception {

        Sensor sensor = new Sensor("Test");
        sensor.setId(1);
        sensor.setCreatedAt(LocalDateTime.now());




        JsonContent<Sensor> result = this.json.write(sensor);

        assertThat(result).hasJsonPathStringValue("$.name");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Test");
        assertThat(result).doesNotHaveJsonPath("$.enabled");
        assertThat(result).hasJsonPathStringValue("$.createdAt");
        assertThat(result).hasJsonPathNumberValue("$.id");
        assertThat(result).hasEmptyJsonPathValue("$.measurements");

    }

    @Test
    public void testSerializeDto() throws Exception {

        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("SensorDTOTest");

        JsonContent<SensorDTO> result = this.sensorDTOJacksonTester.write(sensorDTO);

        System.out.println(result);

        assertThat(result).hasJsonPathStringValue("$.name");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("SensorDTOTest");
        assertThat(result).doesNotHaveJsonPath("$.enabled");
        assertThat(result).doesNotHaveJsonPath("$.createdAt");
        assertThat(result).doesNotHaveJsonPath("$.id");
        assertThat(result).doesNotHaveJsonPath("$.measurements");

    }



}
