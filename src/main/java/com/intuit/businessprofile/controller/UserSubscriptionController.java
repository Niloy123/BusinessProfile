package com.intuit.businessprofile.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.service.UserSubscriptionService;

@RestController
@RequestMapping("/v1/usersubscription")
public class UserSubscriptionController {

	private UserSubscriptionService userSubscriptionService;

	public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
		this.userSubscriptionService = userSubscriptionService;
	}

	@PostMapping("/addSubscription")
	public String addSubscription(@RequestBody @Valid UserSubscriptionRequestDTO userSubscriptionRequestDTO) {
		userSubscriptionService.saveUserScription(userSubscriptionRequestDTO);
		return "User subscribed";
	}
}
