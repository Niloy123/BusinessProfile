package com.intuit.businessprofile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class GetSubscriptionDTO {

	private Boolean isQBAccount;
	private Boolean isQBPayments;
	private Boolean isQBPayroll;
	private Boolean isTSSheets;
}
