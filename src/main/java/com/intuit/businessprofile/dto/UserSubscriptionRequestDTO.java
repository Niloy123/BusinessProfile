package com.intuit.businessprofile.dto;

import lombok.Data;

@Data
public class UserSubscriptionRequestDTO {

	private Boolean isQBAccount;
	private Boolean isQBPayments;
	private Boolean isQBPayroll;
	private Boolean isTSSheets;
}
