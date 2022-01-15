package com.intuit.businessprofile.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.DummyValidatorResponse;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;

@RestController
@RequestMapping("/v1/dummyvalidation")
public class DummyProductVerifyController {

	@PostMapping("/validate")
	public DummyValidatorResponse addProfileValidation(
			@RequestBody @Valid CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {

		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateUpdate")
	public DummyValidatorResponse updateProfileValidation(
			@RequestBody @Valid UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {

		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}
}
