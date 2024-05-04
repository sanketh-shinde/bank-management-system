package com.sanketh.bms.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstname;
	
	private String lastname;
	
	private int age;
	
	@Column(nullable = false, unique = true)
	private String emailid;
	
	private Date dateofbirth;
	
	private String gender;
	
	@Column(nullable = false, unique = true, length = 11)
	private String aadhar;
	
	private String address;
	
	private String password;
	
	@Column(nullable = false, unique = true, length = 10)
	private String mobilenumber;
	
	@Column(nullable = false, unique = true)
	private String accountnumber;
	
	private double amount;

}
