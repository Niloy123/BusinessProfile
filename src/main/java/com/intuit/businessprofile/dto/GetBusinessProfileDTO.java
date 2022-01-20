package com.intuit.businessprofile.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intuit.businessprofile.model.BusinessAddress;
import com.intuit.businessprofile.model.TaxIdentifiers;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class GetBusinessProfileDTO {

	@NotBlank
	private String userId;
	@NotBlank
	private String companyId;
	@NotBlank
	private String companyName;
	@NotBlank
	private String legalName;
	@Valid
	private BusinessAddress businessAddress;
	private String legalAddress;
	@Valid
	private TaxIdentifiers taxIdentifiers;
	private String email;
	private String website;
}
