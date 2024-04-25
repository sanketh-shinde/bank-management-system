package com.sanketh.bms.dao;

import java.sql.Date;
import java.util.List;

import com.sanketh.bms.entities.Transaction;

public interface TransactionDAO {
	Transaction saveTransaction(Transaction transaction);

	List<Transaction> findbyIdandTransactionDetails(int id, Date from, Date to);
}
