package com.intuit.businessprofile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.intuit.businessprofile.client.ProductClient;
import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.enums.Product;
import com.intuit.businessprofile.exceptions.RecordNotFoundException;
import com.intuit.businessprofile.mappers.BusinessProfileMappers;
import com.intuit.businessprofile.model.BusinessProfile;
import com.intuit.businessprofile.model.UserSubscription;
import com.intuit.businessprofile.repository.BusinessProfileCustomRepository;
import com.intuit.businessprofile.repository.BusinessProfileRepository;
import com.intuit.businessprofile.repository.UserSubscriptionRepository;

@Service
public class BusinessProfileServiceImpl implements BusinessProfileService {

	private BusinessProfileRepository businessProfileRepository;
	private BusinessProfileMappers businessProfileMappers;
	private UserSubscriptionRepository userSubscriptionRepository;
	private BusinessProfileCustomRepository businessProfileCustomRepository;
	private Map<Product, ProductClient> productClientMap;

	@Autowired
	public BusinessProfileServiceImpl(@Lazy BusinessProfileRepository businessProfileRepository,
			BusinessProfileMappers businessProfileMappers, @Lazy UserSubscriptionRepository userSubscriptionRepository,
			Map<Product, ProductClient> productClientMap,
			BusinessProfileCustomRepository businessProfileCustomRepository) {
		this.businessProfileRepository = businessProfileRepository;
		this.businessProfileMappers = businessProfileMappers;
		this.userSubscriptionRepository = userSubscriptionRepository;
		this.productClientMap = productClientMap;
		this.businessProfileCustomRepository = businessProfileCustomRepository;
	}

	@Override
	public BusinessProfile saveBusinessProfile(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO)
			throws RecordNotFoundException {
		// TODO Auto-generated method stub

		Optional<UserSubscription> userSubscribedOptional = userSubscriptionRepository
				.findById(createBusinessProfileRequestDTO.getUserId());
		if (userSubscribedOptional.isPresent()) {
			List<Product> productList = new ArrayList<>();
			UserSubscription userSubscription = userSubscribedOptional.get();
			addAvailableSubscriptions(userSubscription, productList);
			for (Product product : productList) {
				productClientMap.get(product).validateProduct(createBusinessProfileRequestDTO);
			}
		} else {
			throw new RecordNotFoundException("UserId passed is not found in the records");
		}
		return businessProfileRepository
				.save(businessProfileMappers.toBusinessProfile(createBusinessProfileRequestDTO));
	}

	private void addAvailableSubscriptions(UserSubscription userSubscription, List<Product> productList) {
		// TODO Auto-generated method stub
		if (userSubscription.getIsQBAccount()) {
			productList.add(Product.QBAccounting);
		}
		if (userSubscription.getIsQBPayments()) {
			productList.add(Product.QBPayments);
		}
		if (userSubscription.getIsQBPayroll()) {
			productList.add(Product.QBPayroll);
		}
		if (userSubscription.getIsTSSheets()) {
			productList.add(Product.TSSheets);
		}
	}

	@Override
	public BusinessProfile updateBusinessProfile(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO)
			throws RecordNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserSubscription> userSubscribedOptional = userSubscriptionRepository
				.findById(updateBusinessProfileRequestDTO.getUserId());
		if (userSubscribedOptional.isPresent()) {
			List<Product> productList = new ArrayList<>();
			UserSubscription userSubscription = userSubscribedOptional.get();
			addAvailableSubscriptions(userSubscription, productList);
			for (Product product : productList) {
				productClientMap.get(product).validateProductForUpdate(updateBusinessProfileRequestDTO);
			}
		} else {
			throw new RecordNotFoundException("UserId passed is not found in the records");
		}
		Optional<BusinessProfile> businessProfileOptional = businessProfileRepository
				.findById(updateBusinessProfileRequestDTO.getUserId());
		BusinessProfile businessProfile = null;
		if (businessProfileOptional.isPresent()) {
			businessProfile = businessProfileOptional.get();
			businessProfileMappers.updateBusinessProfile(businessProfileOptional.get(),
					updateBusinessProfileRequestDTO);
		}
		return businessProfileRepository.save(businessProfile);
	}

}
