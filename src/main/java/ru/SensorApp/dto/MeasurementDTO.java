package ru.SensorApp.dto;

import ru.SensorApp.models.Sensor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class MeasurementDTO {

    @NotNull(message = "Значение не должно пустовать")
    @Min(value = -100, message = "Температура не может быть ниже -100")
    @Max(value = 100, message = "Температура не может быть выше +100")
    private Float value;
    private Optional<Boolean> raining;
    @NotNull(message = "Имя сенсора не должно пустовать")
    private Sensor sensor;
    public Float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    public Optional<Boolean> getRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = Optional.of(raining);
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

}
