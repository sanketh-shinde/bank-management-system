package com.sanketh.bms.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "accounts",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {
						"emailId",
						"aadhar",
						"mobileNumber",
						"accountNumber"
				}),
		}
	)
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private int age;
	
	@Column(nullable = false)
	private String emailId;
	
	@Column(nullable = false)
	private Date dateOfBirth;
	
	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = false, length = 11)
	private String aadhar;
	
	private String address;
	
	private String password;
	
	@Column(nullable = false, length = 10)
	private String mobileNumber;
	
	@Column(nullable = false)
	private String accountNumber;
	
	private double amount;
}
