package com.sanketh.bms.services;

import java.sql.Date;

import com.sanketh.bms.entities.Account;

public interface AccountService {
	Account registration(Account account);

	Account accountLogin(String emailidOrMobile, String password);

	Account getDetailsByEmailIdAndPassword(String emailId, String password);

	Account getDetailsByAccoutNumber(String accountNumber);

	Account passwordUpdation(String emailIdOrPassword, Date date);
}
