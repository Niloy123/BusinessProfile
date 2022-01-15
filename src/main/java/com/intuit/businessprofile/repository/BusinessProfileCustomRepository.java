package com.intuit.businessprofile.repository;

import org.springframework.context.annotation.Lazy;

import com.intuit.businessprofile.model.BusinessProfile;

@Lazy
public interface BusinessProfileCustomRepository {

	BusinessProfile update(BusinessProfile businessProfile);
}
