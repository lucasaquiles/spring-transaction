package com.lucasaquiles.transactions.service;

import com.lucasaquiles.transactions.Repository.WalletRepository;
import com.lucasaquiles.transactions.model.Wallet;
import com.lucasaquiles.transactions.service.validation.WalletValidation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @SneakyThrows

    public Wallet debitValue(final Long walletId, final BigDecimal value){

        Wallet wallet = realizeWalletOperation(walletId, value);

        return walletRepository.save(wallet);
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRED)
    public Wallet debitValueWithTransactional(final Long walletId, final BigDecimal value){

        Wallet wallet = realizeWalletOperation(walletId, value);

        return walletRepository.save(wallet);
    }

    private Wallet realizeWalletOperation(Long walletId, BigDecimal value) throws Exception {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        Wallet wallet = optionalWallet
                .filter(WalletValidation.isValidToDebit(value))
                .orElseThrow(() -> new Exception("saldo insuficiente"));

        BigDecimal diff = wallet.getAmount().subtract(value);
        wallet.setAmount(diff);
        return wallet;
    }
}
