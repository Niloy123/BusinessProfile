package com.intuit.businessprofile.controller;

import javax.validation.Valid;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.CreateUpdateBusinessProfileResponseDTO;
import com.intuit.businessprofile.dto.GetBusinessProfileDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.exceptions.BadRequestException;
import com.intuit.businessprofile.model.BusinessProfile;
import com.intuit.businessprofile.service.BusinessProfileService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/businessprofile")
public class BusinessProfileController {
	private BusinessProfileService businessProfileService;

	public BusinessProfileController(BusinessProfileService businessProfileService) {
		this.businessProfileService = businessProfileService;
	}

	@PostMapping("/createprofile/{userId}")
	@ApiOperation(value = "Create Business Profile", response = CreateUpdateBusinessProfileResponseDTO.class)
	public ResponseEntity<CreateUpdateBusinessProfileResponseDTO> addProfile(
			@RequestBody @Valid CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO,
			@PathVariable final String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			throw new BadRequestException("UserID cannot be empty");
		}
		BusinessProfile businessProfile = businessProfileService.saveBusinessProfile(createBusinessProfileRequestDTO,
				userId);
		CreateUpdateBusinessProfileResponseDTO createUpdateBusinessProfileResponseDTO = new CreateUpdateBusinessProfileResponseDTO();
		createUpdateBusinessProfileResponseDTO.setUserId(businessProfile.getId());
		return new ResponseEntity<CreateUpdateBusinessProfileResponseDTO>(createUpdateBusinessProfileResponseDTO,
				HttpStatus.CREATED);
	}

	@PutMapping("/updateprofile/{userId}")
	@ApiOperation(value = "Update Business Profile", response = CreateUpdateBusinessProfileResponseDTO.class)
	public ResponseEntity<CreateUpdateBusinessProfileResponseDTO> updateProfile(
			@RequestBody @Valid UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO,
			@PathVariable final String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			throw new BadRequestException("UserID cannot be empty");
		}
		BusinessProfile businessProfile = businessProfileService.updateBusinessProfile(updateBusinessProfileRequestDTO,
				userId);
		CreateUpdateBusinessProfileResponseDTO createUpdateBusinessProfileResponseDTO = new CreateUpdateBusinessProfileResponseDTO();
		createUpdateBusinessProfileResponseDTO.setUserId(businessProfile.getId());
		return new ResponseEntity<CreateUpdateBusinessProfileResponseDTO>(createUpdateBusinessProfileResponseDTO,
				HttpStatus.OK);
	}

	@GetMapping("/getprofile/{userId}")
	@ApiOperation(value = "Get Business Profile", response = GetBusinessProfileDTO.class)
	public ResponseEntity<GetBusinessProfileDTO> getProfile(@PathVariable final String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			throw new BadRequestException("UserID cannot be empty");
		}
		GetBusinessProfileDTO getBusinessProfileDTO = businessProfileService.getProfile(userId);
		return new ResponseEntity<GetBusinessProfileDTO>(getBusinessProfileDTO, HttpStatus.OK);
	}
}
