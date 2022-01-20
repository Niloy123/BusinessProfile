package com.intuit.businessprofile.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BusinessAddress {

	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zip;
	private String country;
}
