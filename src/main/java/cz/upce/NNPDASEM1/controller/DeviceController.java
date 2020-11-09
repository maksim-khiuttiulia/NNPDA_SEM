package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.Location;
import cz.upce.NNPDASEM1.domain.device.Device;
import cz.upce.NNPDASEM1.domain.device.Sensor;
import cz.upce.NNPDASEM1.domain.user.User;
import cz.upce.NNPDASEM1.dto.DeviceDto;
import cz.upce.NNPDASEM1.dto.SensorDto;
import cz.upce.NNPDASEM1.service.DeviceService;
import cz.upce.NNPDASEM1.service.LocationService;
import cz.upce.NNPDASEM1.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/devices")
public class DeviceController extends ControllerAncestor {

    private final DeviceService deviceService;

    private final SensorService sensorService;

    private final LocationService locationService;

    @Autowired
    public DeviceController(DeviceService deviceService, SensorService sensorService, LocationService locationService) {
        this.deviceService = deviceService;
        this.sensorService = sensorService;
        this.locationService = locationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DeviceDto> getDevices() {
        User user = getCurrentUser();
        List<Device> devices = deviceService.getDevices(user);
        return devices.stream().map(DeviceDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeviceDto getDevices(@PathVariable("id") Long id) {
        User user = getCurrentUser();
        Device device = deviceService.getDevice(id, user);
        return new DeviceDto(device);
    }

    @RequestMapping(value = "/{id}/sensors", method = RequestMethod.GET)
    public List<SensorDto> getSensors(@PathVariable("id") Long id) {
        User user = getCurrentUser();
        Device device = deviceService.getDevice(id, user);
        List<Sensor> sensors = sensorService.getSensors(device, user);
        return sensors.stream().map(SensorDto::new).collect(Collectors.toList());
    }


    @RequestMapping(method = RequestMethod.POST)
    public DeviceDto createDevice(@RequestBody DeviceDto deviceDto) {
        User user = getCurrentUser();
        Device device = new Device();
        device.setOwner(user);
        device.setName(deviceDto.getName());
        Location location = locationService.getLocation(deviceDto.getLocation().getId());
        device.setLocation(location);
        device = deviceService.saveOrUpdateDevice(device);
        return new DeviceDto(device);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeviceDto updateDevice(@PathVariable("id") Long id, @RequestBody DeviceDto deviceDto) {
        User user = getCurrentUser();
        Device device = deviceService.getDevice(id, user);
        device.setName(deviceDto.getName());
        device = deviceService.saveOrUpdateDevice(device);
        return new DeviceDto(device);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDevice(@PathVariable("id") Long id) {
        User user = getCurrentUser();
        Device device = deviceService.getDevice(id, user);
        deviceService.deleteDevice(device);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/sensors", method = RequestMethod.POST)
    public SensorDto createSensor(@PathVariable("id") Long id, @RequestBody SensorDto sensorDto) {
        User user = getCurrentUser();
        Device device = deviceService.getDevice(id, user);
        Sensor sensor = new Sensor();
        sensor.setName(sensorDto.getName());
        sensor.setDevice(device);
        sensor.setType(sensorDto.getType());
        sensor = sensorService.saveOrUpdateDevice(sensor);
        return new SensorDto(sensor);
    }


    @RequestMapping(value = "/{id}/sensors/{sensorId}", method = RequestMethod.PUT)
    public SensorDto updateSensor(@PathVariable("id") Long id, @PathVariable("sensorId") Long sensorId, @RequestBody SensorDto sensorDto) {
        User user = getCurrentUser();
        Device device = deviceService.getDevice(id, user);
        Sensor sensor = sensorService.getSensor(sensorId, device);
        sensor.setName(sensorDto.getName());
        sensor = sensorService.saveOrUpdateDevice(sensor);
        return new SensorDto(sensor);
    }

    @RequestMapping(value = "/{id}/sensors/{sensorId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> updateSensor(@PathVariable("id") Long id, @PathVariable("sensorId") Long sensorId) {
        User user = getCurrentUser();
        Device device = deviceService.getDevice(id, user);
        Sensor sensor = sensorService.getSensor(sensorId, device);
        sensorService.deleteSensor(sensor);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
