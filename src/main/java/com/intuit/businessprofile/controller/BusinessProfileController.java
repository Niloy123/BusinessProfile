package com.intuit.businessprofile.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.exceptions.RecordNotFoundException;
import com.intuit.businessprofile.exceptions.ValidationException;
import com.intuit.businessprofile.service.BusinessProfileService;

@RestController
@RequestMapping("/v1/businessprofile")
public class BusinessProfileController {
	private BusinessProfileService businessProfileService;

	public BusinessProfileController(BusinessProfileService businessProfileService) {
		this.businessProfileService = businessProfileService;
	}

	@PostMapping("/createprofile")
	public String addProfile(@RequestBody @Valid CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO)
			throws RecordNotFoundException, ValidationException {
		businessProfileService.saveBusinessProfile(createBusinessProfileRequestDTO);
		return "Added profile";
	}

	@PutMapping("/updateprofile")
	public String updateProfile(@RequestBody @Valid UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO)
			throws RecordNotFoundException, ValidationException {
		businessProfileService.updateBusinessProfile(updateBusinessProfileRequestDTO);
		return "Updated profile";
	}
}
