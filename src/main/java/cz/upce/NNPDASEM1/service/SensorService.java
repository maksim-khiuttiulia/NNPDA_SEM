package cz.upce.NNPDASEM1.service;

import cz.upce.NNPDASEM1.domain.device.Device;
import cz.upce.NNPDASEM1.domain.device.Sensor;
import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.exceptions.NotFoundException;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {


    @Autowired
    private SensorRepository sensorRepository;

    public List<Sensor> getSensors(Device device, User user) {
        return sensorRepository.findByDeviceAndDevice_Owner(device, user);
    }

    public Sensor getSensor(Long id, Device device) {
        Optional<Sensor> sensor = sensorRepository.findBySensorIdAndDevice(id, device);
        if (!sensor.isPresent()) {
            throw new NotFoundException("Sensor " + id + " not found");
        }
        return sensor.get();
    }

    public Sensor saveOrUpdateDevice(Sensor sensor) {

        Optional<Sensor> optionalSensor = sensorRepository.findByNameAndDevice(sensor.getName(), sensor.getDevice());
        if (optionalSensor.isPresent()) {
            throw new ValidationException("Sensor with name " + sensor.getName() + " already exists");
        }
        return sensorRepository.save(sensor);
    }

    public void deleteSensor(Sensor sensor) {
        sensorRepository.delete(sensor);
    }
}
