package com.intuit.businessprofile.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "businessprofile")
public class BusinessProfile {

	@Id
	@Indexed(unique = true)
	private String id;
	private String companyId;
	private String companyName;
	private String legalName;
	private BusinessAddress businessAddress;
	private String legalAddress;
	private TaxIdentifiers taxIdentifiers;
	private String email;
	private String website;

}
