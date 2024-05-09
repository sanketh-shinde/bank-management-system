package com.sanketh.bms.controllers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sanketh.bms.entities.Account;
import com.sanketh.bms.entities.Transaction;
import com.sanketh.bms.services.AccountService;
import com.sanketh.bms.services.TransactionService;

@Controller
@SessionAttributes(names = {"account", "otp"})
public class BankController {

	private AccountService accountService;
	
	private TransactionService transactionService;
	
	public BankController(AccountService accountService, TransactionService transactionService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
	}

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@PostMapping("/registrationpage")
	public String showRegistrationPage(Account account) {
		Random random = new Random();
		long accountNo = random.nextLong(100000000000l);
		if (accountNo < 10000000000l) {
			accountNo += 10000000000l;
		}
		String actNo = Long.toString(accountNo);
		account.setAccountNumber(actNo);
		Account registerdAccount = this.accountService.registration(account);
		if (registerdAccount.getId() != null) {
			return "index";
		} else {
			return "registration";
		}
	}

	@PostMapping("/loginpage")
	public String customerlogin(String emailidOrMobile, String password, Model model) {
		Account account = this.accountService.accountLogin(emailidOrMobile, password);
		if (account != null) {
			model.addAttribute("account", account);
			return "homepage";
		} else {
			model.addAttribute("message", "Invalid Details");
			return "index";
		}
	}
	
	@GetMapping("/home")
	public String showHomePage(Model model) {
		return "homepage";
	}

	@GetMapping("/forgotpassword")
	public String forgotpasswordpage() {
		return "forgotpassword";
	}

	@PostMapping("/update")
	public String updatePassword(String emp, Date dob, String newpassword, String confirmpassword, Model model) {
		if (newpassword.equals(confirmpassword)) {
			Account updatedAccount = this.accountService.passwordUpdation(emp, dob);
			if (updatedAccount != null) {
				updatedAccount.setPassword(newpassword);
				this.accountService.registration(updatedAccount);
				return "index";
			} else {
				model.addAttribute("message", "Invalid Details");
				return "forgotpassword";
			}
		} else {
			model.addAttribute("message", "Invalid Password");
			return "forgotpassword";
		}
	}
	
	@GetMapping("/checkbalance")
	public String checkbalance(Model model) {
		Random random = new Random();
		int otp = random.nextInt(10000);
		if (otp < 1000) {
			otp += 1000;
		}
		model.addAttribute("otp", otp);
		model.addAttribute("account");
		return "checkbalance";
	}

	@PostMapping("/processcheckbalance")
	public String fetchingDataforcheckbalance(@RequestParam int userOTP,@RequestParam String userPassword, Model model) {
		Account account = (Account) model.getAttribute("account");
		System.out.println("account model: " + account);
		Integer otp = (Integer) model.getAttribute("otp");
		if (otp == userOTP) {
			account = this.accountService.getDetailsByEmailIdAndPassword(account.getEmailId(), userPassword);
			System.out.println("after: " + account);
			if (account != null) {
				return "userinfo";
			} else {
				Random random = new Random();
				otp = random.nextInt(10000);
				if (otp < 1000) {
					otp += 1000;
				}
				model.addAttribute("message", "Invalid Password");
				return "checkbalance";
			}
		} else {
			Random random = new Random();
			otp = random.nextInt(10000);
			if (otp < 1000) {
				otp += 1000;
			}
			model.addAttribute("message", "Enter valid OTP");
			return "checkbalance";
		}
	}

	@GetMapping("/transfer")
	public String transfer() {
		return "transferamount";
	}

	@PostMapping("/performtransfer")
	public String performDebit(@RequestParam String accountNumber, @RequestParam String amount, Model model) {
		Account account = (Account) model.getAttribute("account");
		Account senderAccount = this.accountService.getDetailsByAccoutNumber(account.getAccountNumber());
		System.out.println(senderAccount);
		double senderAmount = senderAccount.getAmount();
		double inputAmount = Double.parseDouble(amount);
		Account receiverAccount = this.accountService.getDetailsByAccoutNumber(accountNumber);
		if (receiverAccount != null) {
			if (senderAmount >= inputAmount) {
				senderAccount.setAmount(senderAmount - inputAmount);
				account = senderAccount;
				this.accountService.registration(senderAccount);
				receiverAccount.setAmount(receiverAccount.getAmount() + inputAmount);
				this.accountService.registration(receiverAccount);

				Transaction senderTransaction = new Transaction();
				senderTransaction.setCustomerId(senderAccount.getId());
				senderTransaction.setTransactionAmount(inputAmount);
				senderTransaction.setTransactionDate(Date.valueOf(LocalDate.now()));
				senderTransaction.setTransactionTime(String.valueOf(Time.valueOf(LocalTime.now())));
				senderTransaction.setTransactionType("Debit");
				this.transactionService.saveTransaction(senderTransaction);
				
				Transaction recieverTransaction = new Transaction();
				recieverTransaction.setCustomerId(receiverAccount.getId());
				recieverTransaction.setTransactionAmount(inputAmount);
				recieverTransaction.setTransactionDate(Date.valueOf(LocalDate.now()));
				recieverTransaction.setTransactionTime(String.valueOf(Time.valueOf(LocalTime.now())));
				recieverTransaction.setTransactionType("Credit");

				this.transactionService.saveTransaction(recieverTransaction);
				model.addAttribute("message", "Amount Successfully Transferred");
				model.addAttribute("account", account);
				return "userinfo";
			} else {
				model.addAttribute("message", "Invalid Amount");
				return "userinfo";
			}
		} else {
			model.addAttribute("message", "Invalid Account Number");
			return "userinfo";
		}
	}

	@GetMapping("/credit")
	public String credit(Model model) {
		return "creditamount";
	}

	@PostMapping("/performcredit")
	public String performCredit(@RequestParam String accountNumber,@RequestParam String amount, Model model) {
		Account account = (Account) model.getAttribute("account");
		double inputAmount = Double.parseDouble(amount);
		this.accountService.getDetailsByAccoutNumber(accountNumber);
		if (account != null) {
			if (inputAmount > 0) {
				account.setAmount(account.getAmount() + inputAmount);
				this.accountService.registration(account);

				Transaction transaction = new Transaction();
				transaction.setCustomerId(account.getId());
				transaction.setTransactionAmount(inputAmount);
				transaction.setTransactionDate(Date.valueOf(LocalDate.now()));
				transaction.setTransactionTime(String.valueOf(Time.valueOf(LocalTime.now())));
				transaction.setTransactionType("Credit");

				this.transactionService.saveTransaction(transaction);
				model.addAttribute("message", "Amount Successfully Credited");
				return "userinfo";
			} else {
				model.addAttribute("message", "Invalid Amount");
				return "userinfo";
			}
		} else {
			model.addAttribute("message", "Invalid Account Number");
			return "userinfo";
		}
	}

	@GetMapping("/statement")
	public String statement(Model model) {
		return "Statementpage";
	}

	@GetMapping("/statementcheck")
	public String showStatementCheckPage(@RequestParam Date fromDate,@RequestParam Date toDate, Model model) {
		Account account = (Account) model.getAttribute("account");
		List<Transaction> list = this.transactionService.findbyIdandTransactionDetails(account.getId(), fromDate, toDate);
		if (list.isEmpty()) {
			model.addAttribute("message", "No Statements Found");
			return "statement";
		} else {
			model.addAttribute("listOfStatements", list);
			return "statement";
		}
	}
	
	@GetMapping("/updateprofile")
	public String showUpdateProfilePage(Model model) {
		return "updateprofile";
	}
	
	@PostMapping("/processupdateprofile")
	public String processUpdateProfile(@RequestParam String firstName, @RequestParam String lastName, @RequestParam Date dateOfBirth,@RequestParam String address , @RequestParam String mobileNumber, Model model) {
		Account account = (Account) model.getAttribute("account");
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setDateOfBirth(dateOfBirth);
		account.setAddress(address);
		account.setMobileNumber(mobileNumber);
		Account updatedAccount = this.accountService.registration(account);
		if (updatedAccount != null) {
			model.addAttribute("message", "Profile Updated Successfully");
			return "homepage";
		}
		model.addAttribute("message", "Something Went Wrong");
		return "updateprofile";
	}
}
