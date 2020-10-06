package cz.upce.NNPDASEM1.repository;

import cz.upce.NNPDASEM1.domain.device.Device;
import cz.upce.NNPDASEM1.domain.device.Sensor;
import cz.upce.NNPDASEM1.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByDevice(Device device);

    List<Sensor> findByDeviceAndDevice_Owner(Device device, User device_owner);

    Optional<Sensor> findBySensorIdAndDevice(Long sensorId, Device device);

    Optional<Sensor> deleteBySensorIdAndDevice(Long sensorId, Device device);

    Optional<Sensor> findByNameAndDevice(String name, Device device);
}
