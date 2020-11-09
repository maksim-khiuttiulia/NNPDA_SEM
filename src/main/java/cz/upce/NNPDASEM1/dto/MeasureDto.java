package cz.upce.NNPDASEM1.dto;

import cz.upce.NNPDASEM1.domain.Measure;
import lombok.Data;

import java.util.Date;

@Data
public class MeasureDto {
    private Long id;
    private Date date;
    private Double value;
    private SensorDto sensor;

    public MeasureDto() {
    }

    public MeasureDto(Measure measure) {
        this.id = measure.getMeasureId();
        this.value = measure.getValue();
        this.date = measure.getDate();
        this.sensor = new SensorDto(measure.getSensor());
    }
}
