package cz.upce.NNPDASEM1.repository;

import cz.upce.NNPDASEM1.domain.Location;
import cz.upce.NNPDASEM1.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByUser(User user);
}
