package com.intuit.businessprofile.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.DummyValidatorResponse;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/dummyvalidation")
@Slf4j
public class DummyProductVerifyController {

	@PostMapping("/validateQBAccounting")
	public DummyValidatorResponse addQBAccountingProfileValidation(
			@RequestBody @Valid CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateTSSheets")
	public DummyValidatorResponse addTSSheetsProfileValidation(
			@RequestBody @Valid CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateQBPayroll")
	public DummyValidatorResponse addQBPayrollProfileValidation(
			@RequestBody @Valid CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateQBPayments")
	public DummyValidatorResponse addQBPaymentsValidation(
			@RequestBody @Valid CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateUpdateQBAccounting")
	public DummyValidatorResponse updateQBAccountingProfileValidation(
			@RequestBody @Valid UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateUpdateQBPayments")
	public DummyValidatorResponse updateQBPaymentsProfileValidation(
			@RequestBody @Valid UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateUpdateQBPayroll")
	public DummyValidatorResponse updateQBPayrollProfileValidation(
			@RequestBody @Valid UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}

	@PostMapping("/validateUpdateTSSheets")
	public DummyValidatorResponse updateTSSheetsProfileValidation(
			@RequestBody @Valid UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {

		log.info("Threads = {}", Thread.currentThread().getName());
		DummyValidatorResponse dummyProductValidationResponseDTO = new DummyValidatorResponse();
		dummyProductValidationResponseDTO.setSuccess(true);
		return dummyProductValidationResponseDTO;
	}
}
