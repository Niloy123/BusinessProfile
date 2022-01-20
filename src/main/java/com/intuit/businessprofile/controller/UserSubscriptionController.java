package com.intuit.businessprofile.controller;

import javax.validation.Valid;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.businessprofile.dto.GetSubscriptionDTO;
import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.dto.UserSubscriptionResponseDTO;
import com.intuit.businessprofile.exceptions.BadRequestException;
import com.intuit.businessprofile.model.UserSubscription;
import com.intuit.businessprofile.service.UserSubscriptionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/usersubscription")
public class UserSubscriptionController {

	private UserSubscriptionService userSubscriptionService;

	public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
		this.userSubscriptionService = userSubscriptionService;
	}

	@PostMapping("/addSubscription")
	@ApiOperation(value = "Subscribe User Products", response = UserSubscriptionResponseDTO.class)
	public ResponseEntity<UserSubscriptionResponseDTO> addSubscription(
			@RequestBody @Valid UserSubscriptionRequestDTO userSubscriptionRequestDTO) {
		UserSubscription userSubscription = userSubscriptionService.saveUserScription(userSubscriptionRequestDTO);
		UserSubscriptionResponseDTO userSubscriptionResponseDTO = new UserSubscriptionResponseDTO();
		userSubscriptionResponseDTO.setUserId(userSubscription.getId());
		return new ResponseEntity<UserSubscriptionResponseDTO>(userSubscriptionResponseDTO, HttpStatus.OK);
	}

	@GetMapping("/getSubscription/{userId}")
	@ApiOperation(value = "Get Subscribed User Products", response = UserSubscriptionResponseDTO.class)
	public ResponseEntity<GetSubscriptionDTO> getSubscription(@PathVariable final String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			throw new BadRequestException("UserId cannot be null");
		}
		GetSubscriptionDTO getSubscriptionDTO = userSubscriptionService.getSubscription(userId);
		return new ResponseEntity<GetSubscriptionDTO>(getSubscriptionDTO, HttpStatus.OK);
	}

}
