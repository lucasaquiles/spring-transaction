package com.lucasaquiles.transactions.service;


import com.lucasaquiles.transactions.Repository.PaymentRepository;
import com.lucasaquiles.transactions.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private WalletService walletService;


    public void doPayment(final Payment payment){

        realizePayment(payment);
        walletService.debitValue(payment.getWalletId(), payment.getValue());
    }

    private void realizePayment(Payment payment) {
        paymentRepository.save(payment);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void doPaymentWithTransacional(final Payment payment){

        realizePayment(payment);
        walletService.debitValueWithTransactional(payment.getWalletId(), payment.getValue());

    }
}
