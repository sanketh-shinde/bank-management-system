package com.sanketh.bms.services;

import java.sql.Date;
import java.util.List;

import com.sanketh.bms.entities.Transaction;

public interface TransactionService {
	Transaction saveTransaction(Transaction transaction);

	List<Transaction> findbyIdandTransactionDetails(int id, Date from, Date to);
}
