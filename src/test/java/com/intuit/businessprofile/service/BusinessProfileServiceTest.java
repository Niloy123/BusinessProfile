package com.intuit.businessprofile.service;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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

	// @MockBean
	private ThreadPoolTaskExecutor executor;

	@BeforeEach
	public void setup() {

		productClientMap = new HashMap<>();
		productClientMap.put(Product.QBAccounting, qBAccountingClient);
		productClientMap.put(Product.QBPayments, qBPaymentsClient);
		productClientMap.put(Product.QBPayroll, qBPayrollsClient);
		productClientMap.put(Product.TSSheets, tSSheetsClient);
		executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(7);
		executor.setMaxPoolSize(42);
		executor.setQueueCapacity(11);
		executor.setThreadNamePrefix("threadPoolExecutor-");
		executor.initialize();

		businessProfileService = new BusinessProfileServiceImpl(businessProfileRepository, businessProfileMappers,
				userSubscriptionRepository, productClientMap, executor);

	}

	@Test
	public void testSaveBusinessProfileSuccess() throws Exception {

		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId("1234");
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById("1234")).thenReturn(Optional.of(userSubscription));

		when(qBAccountingClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		BusinessProfile actual = businessProfileService.saveBusinessProfile(createBusinessProfileRequestDTO, "1234");
		assertEquals(actual.getId(), businessProfile.getId());
	}

	@Test
	public void testSaveBusinessProfileException() throws Exception {

		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId("randomId");
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById("ffre")).thenReturn(Optional.ofNullable(null));

		when(qBAccountingClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProduct(createBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		assertThrows(RecordNotFoundException.class,
				() -> businessProfileService.saveBusinessProfile(createBusinessProfileRequestDTO, "ffreee"));
	}

	@Test
	public void testUpdateBusinessProfileSuccess() throws Exception {

		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId("1234");
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById("1234")).thenReturn(Optional.of(userSubscription));

		when(qBAccountingClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		BusinessProfile actual = businessProfileService.updateBusinessProfile(updateBusinessProfileRequestDTO, "1234");
		assertEquals(actual.getId(), businessProfile.getId());
	}

	@Test
	public void testUpdateBusinessProfileException() throws RecordNotFoundException, ValidationException {

		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		UserSubscription userSubscription = random(UserSubscription.class);
		userSubscription.setId("3456");
		userSubscription.setIsQBAccount(true);
		userSubscription.setIsQBPayments(true);
		userSubscription.setIsQBPayroll(true);
		userSubscription.setIsTSSheets(true);
		BusinessProfile businessProfile = random(BusinessProfile.class);

		when(userSubscriptionRepository.findById("3456")).thenReturn(Optional.ofNullable(null));

		when(qBAccountingClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPaymentsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(qBPayrollsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);
		when(tSSheetsClient.validateProductForUpdate(updateBusinessProfileRequestDTO)).thenReturn(true);

		when(businessProfileRepository.save(any())).thenReturn(businessProfile);

		assertThrows(RecordNotFoundException.class,
				() -> businessProfileService.updateBusinessProfile(updateBusinessProfileRequestDTO, "3456"));
	}
}
