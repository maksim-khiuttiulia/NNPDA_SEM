package cz.upce.NNPDASEM1.repository;

import cz.upce.NNPDASEM1.domain.device.Device;
import cz.upce.NNPDASEM1.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByOwner(User owner);

    Optional<Device> findByDeviceIdAndOwner(Long deviceId, User owner);

    Optional<Device> findByNameAndOwner(String name, User owner);

    Optional<Device> deleteByDeviceIdAndOwner(Long deviceId, User owner);
}
