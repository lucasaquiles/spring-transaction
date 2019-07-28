package com.lucasaquiles.transactions;

import com.lucasaquiles.transactions.Repository.PaymentRepository;
import com.lucasaquiles.transactions.Repository.WalletRepository;
import com.lucasaquiles.transactions.model.Payment;
import com.lucasaquiles.transactions.model.Wallet;
import com.lucasaquiles.transactions.service.PaymentService;
import com.lucasaquiles.transactions.service.WalletService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TransactionsApplicationTests {

	@Autowired
	private WalletService walletService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	PaymentRepository paymentRepository;



	@Test()
	void testDebitOperationWithoutTransacionalSchema() {

		Wallet wallet = new Wallet();
		wallet.setAmount(new BigDecimal(1000));
		wallet.setUserId(1L);
		wallet.setId(1L);

		walletRepository.save(wallet);

		Payment payment = new Payment();
		payment.setId(1L);
		payment.setLocalDateTime(new Date());
		payment.setValue(new BigDecimal(9900));
		payment.setWalletId(1L);

		Assertions.assertThrows( Exception.class, () -> {

			paymentService.doPayment(payment);
		});

		Assert.assertThat(paymentRepository.findAll(), Matchers.hasSize(1));
	}


	@Test()
	void testDebitOperationTransacionalRequired() {

		Wallet wallet = new Wallet();
		wallet.setAmount(new BigDecimal(1000));
		wallet.setUserId(1L);
		wallet.setId(1L);

		walletRepository.save(wallet);

		Payment payment = new Payment();
		payment.setId(1L);
		payment.setLocalDateTime(new Date());
		payment.setValue(new BigDecimal(9900));
		payment.setWalletId(1L);

		Assertions.assertThrows( Exception.class, () -> {

			paymentService.doPaymentWithTransacional(payment);
		});

		Assert.assertThat(paymentRepository.findAll(), Matchers.empty());
	}


	@Test()
	void testDebit() {

		Wallet wallet = new Wallet();
		wallet.setAmount(new BigDecimal(1000));
		wallet.setUserId(1L);
		wallet.setId(1L);

		walletRepository.save(wallet);

		Payment payment = new Payment();
		payment.setId(1L);
		payment.setLocalDateTime(new Date());
		payment.setValue(new BigDecimal(900));
		payment.setWalletId(1L);

		paymentService.doPaymentWithTransacional(payment);

		wallet = walletRepository.findById(1L).get();

		boolean value = wallet.getAmount().compareTo(new BigDecimal(100)) == 0;
		Assert.assertThat(value, Matchers.is(true)) ;
	}

}
