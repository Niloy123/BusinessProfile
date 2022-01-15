package com.intuit.businessprofile.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usersubscription")
public class UserSubscription {

	@Id
	@Indexed
	private String id;
	private Boolean isQBAccount;
	private Boolean isQBPayments;
	private Boolean isQBPayroll;
	private Boolean isTSSheets;
}
