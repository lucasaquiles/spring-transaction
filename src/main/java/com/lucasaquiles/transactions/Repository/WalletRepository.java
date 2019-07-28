package com.lucasaquiles.transactions.Repository;

import com.lucasaquiles.transactions.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet save(Wallet wallet);
    Optional<Wallet> findById(Long id);
}
