package cz.upce.NNPDASEM1.service;

import cz.upce.NNPDASEM1.domain.device.Device;
import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.exceptions.NotFoundException;
import cz.upce.NNPDASEM1.exceptions.ValidationException;
import cz.upce.NNPDASEM1.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getDevices(User user) {
        return deviceRepository.findByOwner(user);
    }

    public Device getDevice(Long id, User user) {
        Optional<Device> device = deviceRepository.findByDeviceIdAndOwner(id, user);
        if (!device.isPresent()) {
            throw new NotFoundException("Device " + id + " not found");
        }
        return device.get();
    }

    public Device saveOrUpdateDevice(Device device) {
        Optional<Device> optionalDevice = deviceRepository.findByNameAndOwner(device.getName(), device.getOwner());
        if (optionalDevice.isPresent()) {
            throw new ValidationException("Device with name " + device.getName() + " already exists");
        }
        return deviceRepository.save(device);
    }

    public void deleteDevice(Device device) {
        deviceRepository.delete(device);
    }
}
