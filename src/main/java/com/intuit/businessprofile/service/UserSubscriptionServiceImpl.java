package com.intuit.businessprofile.service;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.intuit.businessprofile.dto.GetSubscriptionDTO;
import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.exceptions.RecordNotFoundException;
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
		UserSubscription userSubscription = businessProfileMappers.toUserSubscription(userSubscriptionRequestDTO);
		return userSubscriptionRepository.save(userSubscription);
	}

	@Override
	public GetSubscriptionDTO getSubscription(String userId) throws Exception {
		Optional<UserSubscription> userSubscriptionOptional = userSubscriptionRepository.findById(userId);
		GetSubscriptionDTO getSubscriptionDTO = new GetSubscriptionDTO();
		if (userSubscriptionOptional.isPresent()) {
			UserSubscription userSubscription = userSubscriptionOptional.get();
			if (userSubscription.getIsQBAccount()) {
				getSubscriptionDTO.setIsQBAccount(true);
			}
			if (userSubscription.getIsQBPayments()) {
				getSubscriptionDTO.setIsQBPayments(true);
			}
			if (userSubscription.getIsQBPayroll()) {
				getSubscriptionDTO.setIsQBPayroll(true);
			}
			if (userSubscription.getIsTSSheets()) {
				getSubscriptionDTO.setIsTSSheets(true);
			}
		} else {
			throw new RecordNotFoundException("Record does not exist");
		}
		return getSubscriptionDTO;
	}

}
