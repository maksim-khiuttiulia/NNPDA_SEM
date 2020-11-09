package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.Measure;
import cz.upce.NNPDASEM1.domain.device.Sensor;
import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.dto.MeasureDto;
import cz.upce.NNPDASEM1.dto.SensorDto;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.service.MeasureService;
import cz.upce.NNPDASEM1.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/sensors")
@CrossOrigin
public class SensorsController extends ControllerAncestor {
    @Autowired
    private MeasureService measureService;

    @Autowired
    private SensorService sensorService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SensorDto getSensor(@PathVariable("id") Long id) {
        User user = getCurrentUser();
        Sensor sensor = sensorService.getSensor(id);
        if (!sensor.getDevice().getOwner().equals(user)) {
            throw new ValidationException("Sensor not exists");
        }
        return new SensorDto(sensor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public SensorDto deleteSensor(@PathVariable("id") Long id) {
        User user = getCurrentUser();
        Sensor sensor = sensorService.getSensor(id);
        if (!sensor.getDevice().getOwner().equals(user)) {
            throw new ValidationException("Sensor not exists");
        }
        sensorService.deleteSensor(sensor);
        return new SensorDto(sensor);
    }

    @RequestMapping(value = "/{id}/measures", method = RequestMethod.GET)
    public List<MeasureDto> getMeasures(@PathVariable("id") Long id) {
        User user = getCurrentUser();
        Sensor sensor = sensorService.getSensor(id);
        if (!sensor.getDevice().getOwner().equals(user)) {
            throw new ValidationException("Sensor not exists");
        }
        List<Measure> measures = measureService.getSensorMeasures(id);
        return measures.stream().map(MeasureDto::new).collect(Collectors.toList());
    }
}
