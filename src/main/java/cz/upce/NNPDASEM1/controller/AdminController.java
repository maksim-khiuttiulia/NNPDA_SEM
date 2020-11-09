package cz.upce.NNPDASEM1.controller;

import cz.upce.NNPDASEM1.domain.device.Device;
import cz.upce.NNPDASEM1.dto.DeviceDto;
import cz.upce.NNPDASEM1.service.DeviceService;
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


    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public List<DeviceDto> getDevices() {
        List<Device> devices = deviceService.getDevices();
        return devices.stream().map(DeviceDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
    public DeviceDto getDevice(@PathVariable("id") Long id) {
        return new DeviceDto(deviceService.getDevice(id));
    }

}
