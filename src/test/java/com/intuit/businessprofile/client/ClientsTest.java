package com.intuit.businessprofile.client;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.DummyValidatorResponse;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ClientsTest {

	@MockBean
	private RestTemplate restTemplate;

	private ProductClient productClient;

	@Test
	public void testQBAccountsClient() throws Exception {
		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		productClient = new QBAccountClient(restTemplate);
		HttpEntity<CreateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(createBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateQBAccounting", HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProduct(createBusinessProfileRequestDTO);
	}

	@Test
	public void testQBPaymentsClient() throws Exception {
		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		productClient = new QBPaymentsClient(restTemplate);
		HttpEntity<CreateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(createBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateQBPayments", HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProduct(createBusinessProfileRequestDTO);
	}

	@Test
	public void testQBPayrollClient() throws Exception {
		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		productClient = new QBPayrollClient(restTemplate);
		HttpEntity<CreateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(createBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateQBPayroll", HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProduct(createBusinessProfileRequestDTO);
	}

	@Test
	public void testTSSheetsClient() throws Exception {
		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);
		productClient = new TSheetsClient(restTemplate);
		HttpEntity<CreateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(createBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateTSSheets", HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProduct(createBusinessProfileRequestDTO);
	}

	@Test
	public void testUpdateQBAccountsClient() throws Exception {
		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		productClient = new QBAccountClient(restTemplate);
		HttpEntity<UpdateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(updateBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateUpdateQBAccounting",
				HttpMethod.POST, requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProductForUpdate(updateBusinessProfileRequestDTO);
	}

	@Test
	public void testUpdateQBPaymentsClient() throws Exception {
		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		productClient = new QBPaymentsClient(restTemplate);
		HttpEntity<UpdateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(updateBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateUpdateQBPayments", HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProductForUpdate(updateBusinessProfileRequestDTO);
	}

	@Test
	public void testUpdateQBPayrollClient() throws Exception {
		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		productClient = new QBPayrollClient(restTemplate);
		HttpEntity<UpdateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(updateBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateUpdateQBPayroll", HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProductForUpdate(updateBusinessProfileRequestDTO);
	}

	@Test
	public void testUpdateTSSheetsClient() throws Exception {
		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);
		productClient = new TSheetsClient(restTemplate);
		HttpEntity<UpdateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(updateBusinessProfileRequestDTO);
		when(restTemplate.exchange("http://localhost:8080/v1/dummyvalidation/validateUpdateTSSheets", HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class))
						.thenReturn(new ResponseEntity<DummyValidatorResponse>(random(DummyValidatorResponse.class),
								HttpStatus.OK));
		productClient.validateProductForUpdate(updateBusinessProfileRequestDTO);
	}
}
