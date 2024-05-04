package com.sanketh.bms.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sanketh.bms.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select customerinfo from Customer customerinfo where (customerinfo.emailid=?1 or customerinfo.mobilenumber=?2) and customerinfo.password=?3")
	Customer findByEmailidOrMobilenumberAndPassword(String emailid, String mobilenumber, String password);

	Customer readByEmailidAndPassword(String email, String password);

	Customer getByAccountnumber(String accountnumber);

	Customer findByEmailidOrMobilenumberAndDateofbirth(String emailid, String mobilenumber, Date dateofbirth);

}