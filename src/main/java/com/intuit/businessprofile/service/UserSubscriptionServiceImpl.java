package com.intuit.businessprofile.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.mappers.BusinessProfileMappers;
import com.intuit.businessprofile.model.UserSubscription;
import com.intuit.businessprofile.repository.UserSubscriptionRepository;

@Component
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

	private BusinessProfileMappers businessProfileMappers;
	private UserSubscriptionRepository userSubscriptionRepository;

	public UserSubscriptionServiceImpl(BusinessProfileMappers businessProfileMappers,
			@Lazy UserSubscriptionRepository userSubscriptionRepository) {
		this.userSubscriptionRepository = userSubscriptionRepository;
		this.businessProfileMappers = businessProfileMappers;
	}

	@Override
	public UserSubscription saveUserScription(UserSubscriptionRequestDTO userSubscriptionRequestDTO) {
		// TODO Auto-generated method stub
		UserSubscription userSubscription = businessProfileMappers.toUserSubscription(userSubscriptionRequestDTO);
		return userSubscriptionRepository.save(userSubscription);
	}

}
