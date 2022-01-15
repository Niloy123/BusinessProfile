package com.intuit.businessprofile.dto;

import javax.persistence.Id;

import lombok.Data;

@Data
public class UserSubscriptionRequestDTO {

	@Id
	private String userId;
	private Boolean isQBAccount;
	private Boolean isQBPayments;
	private Boolean isQBPayroll;
	private Boolean isTSSheets;
}
