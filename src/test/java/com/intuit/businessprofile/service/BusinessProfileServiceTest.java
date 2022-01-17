package com.intuit.businessprofile.service;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.intuit.businessprofile.client.ProductClient;
import com.intuit.businessprofile.client.QBAccountClient;
import com.intuit.businessprofile.client.QBPaymentsClient;
import com.intuit.businessprofile.client.QBPayrollClient;
import com.intuit.businessprofile.client.TSheetsClient;
import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.enums.Product;
import com.intuit.businessprofile.exceptions.RecordNotFoundException;
import com.intuit.businessprofile.exceptions.ValidationException;
import com.intuit.businessprofile.mappers.BusinessProfileMappers;
import com.intuit.businessprofile.model.BusinessProfile;
import com.intuit.businessprofile.model.UserSubscription;
import com.intuit.businessprofile.repository.BusinessProfileCustomRepository;
import com.intuit.businessprofile.repository.BusinessProfileRepository;
import com.intuit.businessprofile.repository.UserSubscriptionRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BusinessProfileMappers.class })
public class BusinessProfileServiceTest {

	private BusinessProfileService businessProfileService;

	@MockBean
	private BusinessProfileRepository businessProfileRepository;

	@Autowired
	private BusinessProfileMappers businessProfileMappers;

	@MockBean
	private UserSubscriptionRepository userSubscriptionRepository;

	@MockBean
	private BusinessProfileCustomRepository businessProfileCustomRepository;

	private Map<Product, ProductClient> productClientMap;

	@MockBean
	private QBAccountClient qBAccountingClient;
	@MockBean
	private QBPaymentsClient qBPaymentsClient;
	@MockBean
	private QBPayrollClient qBPayrollsClient;
	@MockBean
	private TSheetsClient tSSheetsClient;

	@MockBean
	private RestTemplate restTemplate;

	@BeforeEach
	public void setup() {
//		qBAccountingClient = new QBAccountClient(restTemplate);
//		qBPaymentsClient = new QBPaymentsClient(restTemplate);
//		qBPayrollsClient = new QBPayrollClient(restTemplate);
//		tSSheetsClient = new TSheetsClient(restTemplate);

		productClientMap = new HashMap<>();
		productClientMap.put(Product.QBAccounting, qBAccountingClient);
		productClientMap.put(Product.QBPayments, qBPaymentsClient);
		productClientMap.put(Product.QBPayroll, qBPayrollsClient);
		productClientMap.put(Product.TSSheets, tSSheetsClient);

		businessProfileService = new BusinessProfileServiceImpl(businessProfileRepository, businessProfileMappers,
				userSubscriptionRepository, productClientMap, businessProfileCustomRepository);

	}

	@Test
	public void testSaveBusinessProfileSuccess() throws RecordNotFoundException, ValidationException {

		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId(createBusinessProfileRequestDTO.getUserId());
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById(createBusinessProfileRequestDTO.getUserId()))
				.thenReturn(Optional.of(userSubscription));

		when(qBAccountingClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		BusinessProfile actual = businessProfileService.saveBusinessProfile(createBusinessProfileRequestDTO);
		assertEquals(actual.getId(), businessProfile.getId());
	}

	@Test
	public void testSaveBusinessProfileException() throws RecordNotFoundException, ValidationException {

		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId("randomId");
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById(createBusinessProfileRequestDTO.getUserId()))
				.thenReturn(Optional.ofNullable(null));

		when(qBAccountingClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		assertThrows(RecordNotFoundException.class,
				() -> businessProfileService.saveBusinessProfile(createBusinessProfileRequestDTO));
	}

	@Test
	public void testUpdateBusinessProfileSuccess() throws RecordNotFoundException, ValidationException {

		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId(updateBusinessProfileRequestDTO.getUserId());
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById(updateBusinessProfileRequestDTO.getUserId()))
				.thenReturn(Optional.of(userSubscription));

		when(qBAccountingClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		BusinessProfile actual = businessProfileService.updateBusinessProfile(updateBusinessProfileRequestDTO);
		assertEquals(actual.getId(), businessProfile.getId());
	}

	@Test
	public void testUpdateBusinessProfileException() throws RecordNotFoundException, ValidationException {

		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId(updateBusinessProfileRequestDTO.getUserId());
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById(updateBusinessProfileRequestDTO.getUserId()))
				.thenReturn(Optional.ofNullable(null));

		when(qBAccountingClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		assertThrows(RecordNotFoundException.class,
				() -> businessProfileService.updateBusinessProfile(updateBusinessProfileRequestDTO));
	}
}
