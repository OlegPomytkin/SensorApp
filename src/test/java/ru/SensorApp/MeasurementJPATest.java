package ru.SensorApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.SensorApp.models.Measurement;
import ru.SensorApp.models.Sensor;
import ru.SensorApp.repositories.MeasurementRepository;
import ru.SensorApp.util.MeasurementNotCreatedException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:test_db",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect"})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeasurementJPATest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MeasurementRepository measurementRepository;

    private Measurement measurement;


    @Before
    public void setUp(){
        measurement = new Measurement(23.2f,true);
        measurement.setCreatedAt(LocalDateTime.now());
        Sensor sensor = new Sensor("TestSensor1");
        entityManager.persist(sensor);
        measurement.setSensor(sensor);
        entityManager.persistAndFlush(measurement);



    }

    @Test
    public void findAllTest(){

        assertThat(measurementRepository.findAll()).isNotEmpty();

        assertThat(measurementRepository.findAll().get(0)).isEqualTo(measurement);

//       assertThat(measurementRepository.countRaining()).isEqualTo(1);

    }

    @Test
    public void ExceptionTest(){

        Measurement measurement2 = new Measurement(23.2f,true);
        Measurement measurement3 = new Measurement(23.2f,true);
        Sensor sensor2 = new Sensor("TestingsSensors");
        measurement3.setSensor(sensor2);


        Exception exception = Assert.assertThrows(RuntimeException.class, ()->{entityManager.persistAndFlush(measurement2);});
        String expect = "Имя сенсора не должно пустовать";
        String actualMessage = exception.getMessage();

        Exception dateException = Assert.assertThrows(RuntimeException.class, ()->{entityManager.persistAndFlush(measurement3);});
        String expectDateException  = "Дата не должна пустовать";
        String actualDateMessage = dateException.getMessage();

        Assert.assertTrue("Имя сеносра не должно пустовать",actualMessage.contains(expect));
        Assert.assertTrue("Дата",!actualDateMessage.contains(expectDateException));

        System.out.println(actualDateMessage);




    }





}
