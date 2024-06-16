package com.switchwon.payments.repository;

import com.switchwon.payments.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    default User findOneById(Long userId) {
        return this.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found with id: " + userId);
        });
    }
}
