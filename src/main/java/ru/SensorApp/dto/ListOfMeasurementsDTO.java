package ru.SensorApp.dto;

import java.util.List;

public class ListOfMeasurementsDTO {
    private List<MeasurementDTOResponse> measurementsList;

    public ListOfMeasurementsDTO(List<MeasurementDTOResponse> measurementsList) {

        this.measurementsList = measurementsList;
    }

    public List<MeasurementDTOResponse> getMeasurementsList() {
        return measurementsList;
    }

    public void setMeasurementsList(List<MeasurementDTOResponse> measurementsList) {

        this.measurementsList = measurementsList;
    }
}
