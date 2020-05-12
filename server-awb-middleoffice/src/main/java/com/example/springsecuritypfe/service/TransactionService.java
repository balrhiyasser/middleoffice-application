package com.example.springsecuritypfe.service;

import java.util.List;

import com.example.springsecuritypfe.model.Transaction;

public interface TransactionService {
	
    Transaction saveTransaction(Transaction transaction);

    Long numberOfTransactions();

    List<Transaction> findAllTransactions();
}
