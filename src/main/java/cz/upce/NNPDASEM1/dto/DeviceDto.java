package cz.upce.NNPDASEM1.dto;

import cz.upce.NNPDASEM1.domain.device.Device;
import lombok.Data;

@Data
public class DeviceDto {
    private Long id;
    private String name;


    public DeviceDto() {
    }

    public DeviceDto(Device device) {
        this.id = device.getDeviceId();
        this.name = device.getName();
    }
}
