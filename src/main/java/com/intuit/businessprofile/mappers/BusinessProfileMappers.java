package com.intuit.businessprofile.mappers;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.model.BusinessAddress;
import com.intuit.businessprofile.model.BusinessProfile;
import com.intuit.businessprofile.model.TaxIdentifiers;
import com.intuit.businessprofile.model.UserSubscription;

@Component
public class BusinessProfileMappers {

	public BusinessProfile toBusinessProfile(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {
		BusinessProfile businessProfile = new BusinessProfile();
		businessProfile.setId(createBusinessProfileRequestDTO.getUserId());
		businessProfile.setCompanyName(createBusinessProfileRequestDTO.getCompanyName());
		businessProfile.setCompanyId(createBusinessProfileRequestDTO.getCompanyId());
		businessProfile.setEmail(createBusinessProfileRequestDTO.getEmail());
		businessProfile.setLegalName(createBusinessProfileRequestDTO.getLegalName());

		if (Objects.nonNull(createBusinessProfileRequestDTO.getBusinessAddress())) {
			BusinessAddress businessAddress = new BusinessAddress();
			businessAddress.setCity(createBusinessProfileRequestDTO.getBusinessAddress().getCity());
			businessAddress.setCountry(createBusinessProfileRequestDTO.getBusinessAddress().getCountry());
			businessAddress.setLine1(createBusinessProfileRequestDTO.getBusinessAddress().getLine1());
			businessAddress.setLine2(createBusinessProfileRequestDTO.getBusinessAddress().getLine2());
			businessAddress.setState(createBusinessProfileRequestDTO.getBusinessAddress().getState());
			businessAddress.setZip(createBusinessProfileRequestDTO.getBusinessAddress().getZip());
			businessProfile.setBusinessAddress(businessAddress);
		}

		if (Objects.nonNull(createBusinessProfileRequestDTO.getTaxIdentifiers())) {
			TaxIdentifiers taxIdentifiers = new TaxIdentifiers();
			taxIdentifiers.setEin(createBusinessProfileRequestDTO.getTaxIdentifiers().getEin());
			taxIdentifiers.setPan(createBusinessProfileRequestDTO.getTaxIdentifiers().getPan());
			businessProfile.setTaxIdentifiers(taxIdentifiers);
		}

		return businessProfile;
	}

	public UserSubscription toUserSubscription(UserSubscriptionRequestDTO userSubscriptionRequestDTO) {
		UserSubscription userSubscription = new UserSubscription();
		userSubscription.setId(userSubscriptionRequestDTO.getUserId());
		userSubscription.setIsQBAccount(userSubscriptionRequestDTO.getIsQBAccount());
		userSubscription.setIsQBPayments(userSubscriptionRequestDTO.getIsQBPayments());
		userSubscription.setIsQBPayroll(userSubscriptionRequestDTO.getIsQBPayroll());
		userSubscription.setIsTSSheets(userSubscriptionRequestDTO.getIsTSSheets());

		return userSubscription;
	}

	public BusinessProfile updateBusinessProfile(BusinessProfile businessProfile,
			UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {
		if (updateBusinessProfileRequestDTO.getCompanyId() != null) {
			businessProfile.setCompanyId(updateBusinessProfileRequestDTO.getCompanyId());
		}
		if (updateBusinessProfileRequestDTO.getCompanyName() != null) {
			businessProfile.setCompanyName(updateBusinessProfileRequestDTO.getCompanyName());
		}
		if (updateBusinessProfileRequestDTO.getEmail() != null) {
			businessProfile.setEmail(updateBusinessProfileRequestDTO.getEmail());
		}
		if (updateBusinessProfileRequestDTO.getWebsite() != null) {
			businessProfile.setWebsite(updateBusinessProfileRequestDTO.getWebsite());
		}
		if (updateBusinessProfileRequestDTO.getLegalName() != null) {
			businessProfile.setLegalName(updateBusinessProfileRequestDTO.getLegalName());
		}
		if (updateBusinessProfileRequestDTO.getBusinessAddress() != null) {
			if (updateBusinessProfileRequestDTO.getBusinessAddress().getCity() != null) {
				businessProfile.getBusinessAddress()
						.setCity(updateBusinessProfileRequestDTO.getBusinessAddress().getCity());
			}
			if (updateBusinessProfileRequestDTO.getBusinessAddress().getCountry() != null) {
				businessProfile.getBusinessAddress()
						.setCountry(updateBusinessProfileRequestDTO.getBusinessAddress().getCountry());
			}
			if (updateBusinessProfileRequestDTO.getBusinessAddress().getState() != null) {
				businessProfile.getBusinessAddress()
						.setState(updateBusinessProfileRequestDTO.getBusinessAddress().getState());
			}
			if (updateBusinessProfileRequestDTO.getBusinessAddress().getLine1() != null) {
				businessProfile.getBusinessAddress()
						.setLine1(updateBusinessProfileRequestDTO.getBusinessAddress().getLine1());
			}
			if (updateBusinessProfileRequestDTO.getBusinessAddress().getLine2() != null) {
				businessProfile.getBusinessAddress()
						.setLine2(updateBusinessProfileRequestDTO.getBusinessAddress().getLine2());
			}
			if (updateBusinessProfileRequestDTO.getBusinessAddress().getZip() != null) {
				businessProfile.getBusinessAddress()
						.setZip(updateBusinessProfileRequestDTO.getBusinessAddress().getZip());
			}
		}
		if (updateBusinessProfileRequestDTO.getTaxIdentifiers() != null) {
			if (updateBusinessProfileRequestDTO.getTaxIdentifiers().getEin() != null) {
				businessProfile.getTaxIdentifiers()
						.setEin(updateBusinessProfileRequestDTO.getTaxIdentifiers().getEin());
			}
			if (updateBusinessProfileRequestDTO.getTaxIdentifiers().getPan() != null) {
				businessProfile.getTaxIdentifiers()
						.setPan(updateBusinessProfileRequestDTO.getTaxIdentifiers().getPan());
			}
		}
		return businessProfile;
	}
}
