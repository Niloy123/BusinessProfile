package com.intuit.businessprofile.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.intuit.businessprofile.model.BusinessAddress;
import com.intuit.businessprofile.model.TaxIdentifiers;

import lombok.Data;

@Data
public class UpdateBusinessProfileRequestDTO {

	@NotBlank
	private String companyId;
	@NotBlank
	private String companyName;
	@NotBlank
	private String legalName;
	private BusinessAddress businessAddress;
	private String legalAddress;
	@Valid
	private TaxIdentifiers taxIdentifiers;
	private String email;
	private String website;
}
