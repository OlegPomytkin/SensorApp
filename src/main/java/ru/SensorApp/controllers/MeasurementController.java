package ru.SensorApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.SensorApp.dto.CountRainyDaysDTO;
import ru.SensorApp.dto.ListOfMeasurementsDTO;
import ru.SensorApp.dto.MeasurementDTO;
import ru.SensorApp.dto.MeasurementDTOResponse;
import ru.SensorApp.models.Measurement;
import ru.SensorApp.services.MeasurementService;
import ru.SensorApp.util.MeasurementErrorResponse;
import ru.SensorApp.util.MeasurementNotCreatedException;
import ru.SensorApp.util.MeasurementValidator;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {

        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping("")
    public ListOfMeasurementsDTO getMeasurements() {

        ListOfMeasurementsDTO listOfMeasurementsDTO = new ListOfMeasurementsDTO(measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList()));

        return listOfMeasurementsDTO;
    }

    @GetMapping("/rainyDaysCount")
    public CountRainyDaysDTO getCountRainDays() {

        CountRainyDaysDTO countRainyDaysDTO = new CountRainyDaysDTO();
        countRainyDaysDTO.setCountRainingDays(measurementService.countRaining());

        return countRainyDaysDTO;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }
        else {
            measurementService.save(convertToMeasurement(measurementDTO));
            //Пустой Http ответ статус 200
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {

        Measurement measurement = new Measurement();
        measurement.setSensor(measurementDTO.getSensor());
        measurement.setRaining(measurementDTO.getRaining().get());
        measurement.setValue(measurementDTO.getValue());

        return measurement;
    }

    private MeasurementDTOResponse convertToMeasurementDTO(Measurement measurement) {

        return modelMapper.map(measurement, MeasurementDTOResponse.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {

        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
