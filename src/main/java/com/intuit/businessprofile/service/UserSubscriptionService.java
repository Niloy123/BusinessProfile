package com.intuit.businessprofile.service;

import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.model.UserSubscription;

public interface UserSubscriptionService {

	UserSubscription saveUserScription(UserSubscriptionRequestDTO userSubscriptionRequestDTO);
}
