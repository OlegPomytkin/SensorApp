package ru.SensorApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.SensorApp.models.Sensor;
import ru.SensorApp.repositories.SensorRepository;
import ru.SensorApp.util.SensorNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findOne(int id) {

        Optional<Sensor> foundSensor = sensorRepository.findById(id);

        return foundSensor.orElseThrow(SensorNotFoundException::new);
    }

    public List<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {

        enrichSensor(sensor);
        sensorRepository.save(sensor);
    }
    public void enrichSensor(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
    }

}

