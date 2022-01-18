package com.intuit.businessprofile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.intuit.businessprofile.client.ProductClient;
import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.enums.Product;
import com.intuit.businessprofile.exceptions.BadRequestException;
import com.intuit.businessprofile.exceptions.RecordNotFoundException;
import com.intuit.businessprofile.exceptions.ValidationException;
import com.intuit.businessprofile.mappers.BusinessProfileMappers;
import com.intuit.businessprofile.model.BusinessProfile;
import com.intuit.businessprofile.model.UserSubscription;
import com.intuit.businessprofile.repository.BusinessProfileRepository;
import com.intuit.businessprofile.repository.UserSubscriptionRepository;

@Service
public class BusinessProfileServiceImpl implements BusinessProfileService {

	private BusinessProfileRepository businessProfileRepository;
	private BusinessProfileMappers businessProfileMappers;
	private UserSubscriptionRepository userSubscriptionRepository;
	private Map<Product, ProductClient> productClientMap;
	private Executor executor;

	@Autowired
	public BusinessProfileServiceImpl(@Lazy BusinessProfileRepository businessProfileRepository,
			BusinessProfileMappers businessProfileMappers, @Lazy UserSubscriptionRepository userSubscriptionRepository,
			Map<Product, ProductClient> productClientMap, @Qualifier("threadPoolExecutor") Executor executor) {
		this.businessProfileRepository = businessProfileRepository;
		this.businessProfileMappers = businessProfileMappers;
		this.userSubscriptionRepository = userSubscriptionRepository;
		this.productClientMap = productClientMap;
		this.executor = executor;
	}

	@Override
	public BusinessProfile saveBusinessProfile(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO,
			String userId) throws Exception {

		// Check already present
		Optional<BusinessProfile> businessProfileOptional = businessProfileRepository.findById(userId);
		if (businessProfileOptional.isPresent()) {
			throw new BadRequestException("Profile already exists");
		}

		Optional<UserSubscription> userSubscribedOptional = userSubscriptionRepository.findById(userId);
		if (userSubscribedOptional.isPresent()) {
			List<Product> productList = new ArrayList<>();
			UserSubscription userSubscription = userSubscribedOptional.get();
			addAvailableSubscriptions(userSubscription, productList);
			List<CompletableFuture<Boolean>> completableFutures = productList.stream()
					.map(product -> validateCreateAsync(product, createBusinessProfileRequestDTO))
					.collect(Collectors.toList());

			List<Boolean> results = completableFutures.stream().map(CompletableFuture::join)
					.collect(Collectors.toList());
			if (results.contains(false)) {
				throw new ValidationException("Validation failed");
			}
//			for (Product product : productList) {
//				productClientMap.get(product).validateProduct(createBusinessProfileRequestDTO);
//			}
		} else {
			throw new RecordNotFoundException("UserId passed is not found in the records");
		}
		return businessProfileRepository
				.save(businessProfileMappers.toBusinessProfile(createBusinessProfileRequestDTO, userId));
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
	public BusinessProfile updateBusinessProfile(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO,
			String userId) throws RecordNotFoundException, ValidationException {
		// TODO Auto-generated method stub
		Optional<UserSubscription> userSubscribedOptional = userSubscriptionRepository.findById(userId);
		if (userSubscribedOptional.isPresent()) {
			List<Product> productList = new ArrayList<>();
			UserSubscription userSubscription = userSubscribedOptional.get();
			addAvailableSubscriptions(userSubscription, productList);
			List<CompletableFuture<Boolean>> completableFutures = productList.stream()
					.map(product -> validateUpdateAsync(product, updateBusinessProfileRequestDTO))
					.collect(Collectors.toList());

			List<Boolean> results = completableFutures.stream().map(CompletableFuture::join)
					.collect(Collectors.toList());
			if (results.contains(false)) {
				throw new ValidationException("Validation failed");
			}
//			for (Product product : productList) {
//				productClientMap.get(product).validateProductForUpdate(updateBusinessProfileRequestDTO);
//			}
		} else {
			throw new RecordNotFoundException("UserId passed is not found in the records");
		}
		Optional<BusinessProfile> businessProfileOptional = businessProfileRepository.findById(userId);
		BusinessProfile businessProfile = null;
		if (businessProfileOptional.isPresent()) {
			businessProfile = businessProfileOptional.get();
			businessProfileMappers.updateBusinessProfile(businessProfileOptional.get(),
					updateBusinessProfileRequestDTO);
		}
		return businessProfileRepository.save(businessProfile);
	}

	private CompletableFuture<Boolean> validateUpdateAsync(Product product,
			UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {

		CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(
				() -> productClientMap.get(product).validateProductForUpdate(updateBusinessProfileRequestDTO),
				executor);
		return completableFuture;
	}

	private CompletableFuture<Boolean> validateCreateAsync(Product product,
			CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {

		CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(
				() -> productClientMap.get(product).validateProduct(createBusinessProfileRequestDTO), executor);
		return completableFuture;
	}

}
