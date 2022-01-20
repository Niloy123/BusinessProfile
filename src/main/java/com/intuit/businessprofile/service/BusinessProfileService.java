package com.intuit.businessprofile.service;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.GetBusinessProfileDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.model.BusinessProfile;

public interface BusinessProfileService {

	BusinessProfile saveBusinessProfile(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO, String userId)
			throws Exception;

	BusinessProfile updateBusinessProfile(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO,
			String userId) throws Exception;

	GetBusinessProfileDTO getProfile(String userId) throws Exception;
}
