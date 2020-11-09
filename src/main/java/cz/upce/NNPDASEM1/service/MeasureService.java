package cz.upce.NNPDASEM1.service;

import cz.upce.NNPDASEM1.domain.Measure;
import cz.upce.NNPDASEM1.repository.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MeasureService {

    @Autowired
    private MeasureRepository measureRepository;

    public Measure saveMeasure(Measure measure) {
        if (measure.getDate() == null) {
            measure.setDate(new Date());
        }
        return measureRepository.save(measure);
    }

    public List<Measure> getSensorMeasures(Long sensorId) {
        return measureRepository.findBySensor_SensorIdOrderByDateDesc(sensorId);
    }
}
