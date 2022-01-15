package com.intuit.businessprofile.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TaxIdentifiers {

	@NotBlank
	private String pan;
	@NotBlank
	private String ein;
}
