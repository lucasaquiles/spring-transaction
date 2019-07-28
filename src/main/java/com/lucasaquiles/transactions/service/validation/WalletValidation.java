package com.lucasaquiles.transactions.service.validation;


import com.lucasaquiles.transactions.model.Wallet;

import java.math.BigDecimal;
import java.util.function.Predicate;


public class WalletValidation {

    public static Predicate<Wallet> isValidToDebit(BigDecimal value){

        return p -> p.getAmount().compareTo(value) >= 0;
     }
}
