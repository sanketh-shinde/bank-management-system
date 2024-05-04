package com.sanketh.bms.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanketh.bms.entities.Transaction;
import com.sanketh.bms.repository.TransactionRepository;

@Component
public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> findbyIdandTransactionDetails(int id, Date from, Date to) {
		return transactionRepository
				.findByCustomeridAndTransactiondateGreaterThanEqualAndTransactiondateLessThanEqual(id, from, to);
	}

}
