package com.infy.carservice.auth.repository;
import com.infy.carservice.auth.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByEmail(String email);
}
