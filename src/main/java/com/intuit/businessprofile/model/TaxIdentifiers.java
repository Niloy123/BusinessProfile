package com.intuit.businessprofile.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class TaxIdentifiers {

	@NotBlank
	private String pan;
	@NotBlank
	private String ein;
}
