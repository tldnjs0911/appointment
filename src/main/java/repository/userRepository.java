package repository;

import entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user, Long> {
    user findByUsername(String username);
}
