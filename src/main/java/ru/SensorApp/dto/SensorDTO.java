package ru.SensorApp.dto;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Наименование сенсора не должно пустовать")
    @Size(min = 3, max = 30, message = "Наименование сенсора должно быть от 3 до 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
