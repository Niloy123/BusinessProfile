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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.intuit.businessprofile.client.ProductClient;
import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.GetBusinessProfileDTO;
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
		} else {
			throw new RecordNotFoundException("UserId passed is not found in the records");
		}
		return businessProfileRepository
				.save(businessProfileMappers.toBusinessProfile(createBusinessProfileRequestDTO, userId));
	}

	private void addAvailableSubscriptions(UserSubscription userSubscription, List<Product> productList) {

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
			String userId) throws Exception {

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

		} else {
			throw new RecordNotFoundException("UserId passed is not found in the records");
		}
		Optional<BusinessProfile> businessProfileOptional = businessProfileRepository.findById(userId);
		BusinessProfile businessProfile = null;
		if (businessProfileOptional.isPresent()) {
			businessProfile = businessProfileOptional.get();
			businessProfileMappers.updateBusinessProfile(businessProfileOptional.get(),
					updateBusinessProfileRequestDTO);
		} else {
			throw new RecordNotFoundException("Record does not exist");
		}
		return businessProfileRepository.save(businessProfile);
	}

	@Override
	public GetBusinessProfileDTO getProfile(String userId) throws Exception {

		Optional<BusinessProfile> businessProfileOptional = businessProfileRepository.findById(userId);
		GetBusinessProfileDTO getBusinessProfileDTO = new GetBusinessProfileDTO();
		if (businessProfileOptional.isPresent()) {
			BusinessProfile businessProfile = businessProfileOptional.get();
			getBusinessProfileDTO = businessProfileMappers.toBusinessProfileDTO(userId, businessProfile);
		} else {
			throw new RecordNotFoundException("Record does not exist");
		}
		return getBusinessProfileDTO;
	}

	private CompletableFuture<Boolean> validateUpdateAsync(Product product,
			UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {

		CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return productClientMap.get(product).validateProductForUpdate(updateBusinessProfileRequestDTO);
			} catch (Exception e) {
				throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
			}
		}, executor);
		return completableFuture;
	}

	private CompletableFuture<Boolean> validateCreateAsync(Product product,
			CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {

		CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return productClientMap.get(product).validateProduct(createBusinessProfileRequestDTO);
			} catch (Exception e) {
				throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
			}
		}, executor);
		return completableFuture;
	}
}
