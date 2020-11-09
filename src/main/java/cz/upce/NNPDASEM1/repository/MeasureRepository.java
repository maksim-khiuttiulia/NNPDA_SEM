package cz.upce.NNPDASEM1.repository;

import cz.upce.NNPDASEM1.domain.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {
    List<Measure> findBySensor_SensorIdOrderByDateDesc(Long sensorId);
}
