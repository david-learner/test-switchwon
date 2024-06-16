package com.switchwon.payments.repository;

import com.switchwon.payments.domain.Merchant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    Optional<Merchant> findByUserMerchantId(String userMerchantId);
}
