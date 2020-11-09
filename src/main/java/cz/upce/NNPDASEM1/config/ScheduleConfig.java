package cz.upce.NNPDASEM1.config;

import cz.upce.NNPDASEM1.domain.Measure;
import cz.upce.NNPDASEM1.domain.device.Sensor;
import cz.upce.NNPDASEM1.domain.device.SensorType;
import cz.upce.NNPDASEM1.service.MeasureService;
import cz.upce.NNPDASEM1.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
public class ScheduleConfig {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private MeasureService measureService;

    @Scheduled(fixedDelay = 60000)
    public void addMeasures() {
        System.out.println("Adding measures");
        List<Sensor> sensors = sensorService.getSensors();
        for (Sensor sensor : sensors) {
            Measure measure = new Measure();
            Double value = Math.random() * 30 + 10;
            if (sensor.getType() == SensorType.THERMOMETER) {
                value = Math.random() * 10 + 20;
            }
            if (sensor.getType() == SensorType.WATER_FLOW_SENSOR) {
                value = Math.random() * 10;
            }
            if (sensor.getType() == SensorType.ELECTRICITY_SENSOR) {
                value = Math.random() * 5 + 1;
            }
            measure.setValue(value);
            measure.setSensor(sensor);
            measureService.saveMeasure(measure);
        }
    }
}
