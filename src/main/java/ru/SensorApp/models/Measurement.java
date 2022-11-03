package ru.SensorApp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull(message = "Значение не должно пустовать")
    @Min(value = -100, message = "Температура не может быть ниже -100")
    @Max(value = 100, message = "Температура не может быть выше +100")
    private float value;

    @Column(name = "raining")
    @NotNull(message = "Индикация дождя не должна пустовать")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name="sensor", referencedColumnName = "id")
    @NotNull(message = "Имя сенсора не должно пустовать")
    private Sensor sensor;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Measurement() {}

    public Measurement(float value, boolean raining) {

        this.value = value;
        this.raining = raining;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public float getValue() {
        return value;
    }
    public boolean isRaining() {
        return raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

}
