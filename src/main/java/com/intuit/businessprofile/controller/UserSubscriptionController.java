package com.intuit.businessprofile.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.dto.UserSubscriptionResponseDTO;
import com.intuit.businessprofile.model.UserSubscription;
import com.intuit.businessprofile.service.UserSubscriptionService;

@RestController
@RequestMapping("/v1/usersubscription")
public class UserSubscriptionController {

	private UserSubscriptionService userSubscriptionService;

	public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
		this.userSubscriptionService = userSubscriptionService;
	}

	@PostMapping("/addSubscription")
	public ResponseEntity<UserSubscriptionResponseDTO> addSubscription(
			@RequestBody @Valid UserSubscriptionRequestDTO userSubscriptionRequestDTO) {
		UserSubscription userSubscription = userSubscriptionService.saveUserScription(userSubscriptionRequestDTO);
		UserSubscriptionResponseDTO userSubscriptionResponseDTO = new UserSubscriptionResponseDTO();
		userSubscriptionResponseDTO.setUserId(userSubscription.getId());
		return new ResponseEntity<UserSubscriptionResponseDTO>(userSubscriptionResponseDTO, HttpStatus.OK);
	}
}
