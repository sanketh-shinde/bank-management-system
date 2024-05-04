package com.sanketh.bms.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanketh.bms.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByCustomerIdAndTransactionDateGreaterThanEqualAndTransactionDateLessThanEqual(Integer id, Date from, Date to);
}
