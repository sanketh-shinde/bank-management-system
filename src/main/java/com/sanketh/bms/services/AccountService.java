package com.sanketh.bms.dao;

import java.sql.Date;

import com.sanketh.bms.entities.Customer;

public interface CustomerDAO {
	Customer registration(Customer customerDetails);

	Customer customerLogin(String emailidormob, String password);

	Customer getDetailsByEmailIdAndPassword(String emailId, String password);

	Customer getDetailsByAccoutnumber(String actNo);

	Customer passwordUpdation(String emailorpassword, Date date);
}
