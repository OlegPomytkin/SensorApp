package ru.SensorApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.SensorApp.models.Measurement;
import ru.SensorApp.repositories.MeasurementRepository;
import ru.SensorApp.repositories.SensorRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public long countRaining() {
        return measurementRepository.countRaining();
    }

    @Transactional
    public void save(Measurement measurement) {

        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {

        measurement.setSensor(sensorRepository.findByName(measurement.getSensor().getName()).get(0));
        measurement.setCreatedAt(LocalDateTime.now());

    }

}
