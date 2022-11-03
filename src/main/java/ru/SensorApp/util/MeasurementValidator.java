package ru.SensorApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.SensorApp.dto.MeasurementDTO;
import ru.SensorApp.services.MeasurementService;
import ru.SensorApp.services.SensorService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementValidator(SensorService sensorService, MeasurementService measurementService) {

        this.sensorService = sensorService;
        this.measurementService = measurementService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MeasurementDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        MeasurementDTO measurementDTO = (MeasurementDTO) o;

        if (measurementDTO.getSensor() != null && sensorService.findByName(measurementDTO.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "", "Сенсор с таким наименованием не зарегистрирован в системе");
        try {
            measurementDTO.getRaining().get();
        } catch (NullPointerException e) {
            errors.rejectValue("raining", "", "Индикация дождя не должна пустовать");
        }
    }
}
