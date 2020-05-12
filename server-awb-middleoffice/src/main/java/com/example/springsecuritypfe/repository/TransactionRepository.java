package com.example.springsecuritypfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
