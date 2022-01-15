package com.intuit.businessprofile.model;

import lombok.Data;

@Data
public class BusinessAddress {

	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zip;
	private String country;
}
