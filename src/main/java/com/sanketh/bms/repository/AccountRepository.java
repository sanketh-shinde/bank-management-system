package com.sanketh.bms.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sanketh.bms.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query("select account from Account account where (account.emailId=?1 or account.mobileNumber=?2) and account.password=?3")
	Account findByEmailIdOrMobileNumberAndPassword(String emailId, String mobileNumber, String password);

	Account findByEmailIdAndPassword(String emailId, String password);

	Account findByAccountNumber(String accountnumber);

	Account findByEmailIdOrMobileNumberAndDateOfBirth(String emailId, String mobileNumber, Date dateOfBirth);

}