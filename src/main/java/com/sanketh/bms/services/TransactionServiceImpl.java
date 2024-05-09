package com.sanketh.bms.services;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sanketh.bms.entities.Transaction;
import com.sanketh.bms.repository.TransactionRepository;

@Component
public class TransactionServiceImpl implements TransactionService {

	private TransactionRepository transactionRepository;
	
	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	@Override
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> findbyIdandTransactionDetails(int id, Date from, Date to) {
		return transactionRepository.findByCustomerIdAndTransactionDateGreaterThanEqualAndTransactionDateLessThanEqual(id, from, to);
	}

}
