package ru.SensorApp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MeasurementDTOResponse {
    @NotNull(message = "Значение не должно пустовать")
    @Min(value = -100, message = "Температура не может быть ниже -100")
    @Max(value = 100, message = "Температура не может быть выше +100")
    private float value;
    @NotNull (message = "Индикация дождя не должна пустовать")
    private boolean raining;
    @NotNull(message = "Имя сенсора не должно пустовать")
    private SensorDTO sensor;
    private LocalDateTime createdAt;

    public float getValue() {
        return value;
    }

    public boolean isRaining() {
        return raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
