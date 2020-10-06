package cz.upce.NNPDASEM1.repository;

import cz.upce.NNPDASEM1.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
