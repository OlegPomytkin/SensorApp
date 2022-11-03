package ru.SensorApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.SensorApp.dto.SensorDTO;
import ru.SensorApp.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SensorDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        SensorDTO sensor = (SensorDTO) o;

        if(!sensorService.findByName(sensor.getName()).isEmpty())
            errors.rejectValue("name","","Сенсор с таким наименованием уже зарегистрирован");
    }

}
