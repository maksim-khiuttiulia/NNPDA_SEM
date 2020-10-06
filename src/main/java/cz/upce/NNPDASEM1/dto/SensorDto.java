package cz.upce.NNPDASEM1.dto;

import cz.upce.NNPDASEM1.domain.device.Sensor;
import lombok.Data;

@Data
public class SensorDto {
    private Long id;
    private String name;

    public SensorDto() {
    }

    public SensorDto(Sensor sensor) {
        this.id = sensor.getSensorId();
        this.name = sensor.getName();
    }
}
