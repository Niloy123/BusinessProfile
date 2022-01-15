package com.intuit.businessprofile.service;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.exceptions.RecordNotFoundException;
import com.intuit.businessprofile.exceptions.ValidationException;
import com.intuit.businessprofile.model.BusinessProfile;

public interface BusinessProfileService {

	BusinessProfile saveBusinessProfile(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO)
			throws RecordNotFoundException, ValidationException;

	BusinessProfile updateBusinessProfile(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO)
			throws RecordNotFoundException, ValidationException;
}
