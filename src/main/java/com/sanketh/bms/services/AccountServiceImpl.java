package com.sanketh.bms.services;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.sanketh.bms.entities.Account;
import com.sanketh.bms.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account registration(Account account) {
		return this.accountRepository.save(account);
	}

	@Override
	public Account accountLogin(String emailIdOrPassword, String password) {
		return this.accountRepository.findByEmailIdOrMobileNumberAndPassword(emailIdOrPassword, emailIdOrPassword, password);
	}

	@Override
	public Account getDetailsByEmailIdAndPassword(String emailId, String password) {
		return this.accountRepository.findByEmailIdAndPassword(emailId, password);
	}

	@Override
	public Account getDetailsByAccoutNumber(String accountNumber) {
		return this.accountRepository.findByAccountNumber(accountNumber);
	}

	@Override
	public Account passwordUpdation(String emailorpassword, Date date) {
		return this.accountRepository.findByEmailIdOrMobileNumberAndDateOfBirth(emailorpassword, emailorpassword, date);
	}

}
