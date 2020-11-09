package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.device.Device;
import cz.upce.NNPDASEM1.dto.DeviceDto;
import cz.upce.NNPDASEM1.dto.MeasureDto;
import cz.upce.NNPDASEM1.dto.SensorDto;
import cz.upce.NNPDASEM1.service.DeviceService;
import cz.upce.NNPDASEM1.service.MeasureService;
import cz.upce.NNPDASEM1.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
@CrossOrigin
public class AdminController extends ControllerAncestor {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private MeasureService measureService;


    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public List<DeviceDto> getDevices() {
        List<Device> devices = deviceService.getDevices();
        return devices.stream().map(DeviceDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
    public DeviceDto getDevice(@PathVariable("id") Long id) {
        return new DeviceDto(deviceService.getDevice(id));
    }

    @RequestMapping(value = "/devices/{id}/sensors", method = RequestMethod.GET)
    public List<SensorDto> getDeviceSensors(@PathVariable("id") Long id) {
        Device device = deviceService.getDevice(id);
        return sensorService.getSensors(device).stream().map(SensorDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/sensors/{id}", method = RequestMethod.GET)
    public SensorDto getSensor(@PathVariable("id") Long id) {
        return new SensorDto(sensorService.getSensor(id));
    }

    @RequestMapping(value = "/sensors/{id}/measures", method = RequestMethod.GET)
    public List<MeasureDto> getSensorMeasures(@PathVariable("id") Long id) {
        return measureService.getSensorMeasures(id).stream().map(MeasureDto::new).collect(Collectors.toList());
    }
}
