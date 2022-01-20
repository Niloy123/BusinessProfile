package com.intuit.businessprofile.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.intuit.businessprofile.constants.Constants;
import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.DummyValidatorResponse;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.enums.Product;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QBPayrollClient implements ProductClient {

	private RestTemplate restTemplate;

	public QBPayrollClient(@Qualifier("genericClient") RestTemplate restTemplate) {
		// TODO Auto-generated constructor stub
		this.restTemplate = restTemplate;
	}

	@Override
	public boolean validateProduct(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {
		// TODO Auto-generated method stub
		log.debug("Calling validate endpoint with request={} ", createBusinessProfileRequestDTO);
		UriComponents uri = UriComponentsBuilder.fromUriString(Constants.VALIDATION_URL_BASE)
				.path(Constants.VALIDATION_QB_PAYROLLS_URL_PATH).build();
		HttpEntity<CreateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(createBusinessProfileRequestDTO);
		ResponseEntity<DummyValidatorResponse> responseEntity;
		try {
			responseEntity = restTemplate.exchange(uri.toString(), HttpMethod.POST, requestEntity,
					DummyValidatorResponse.class);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
		}
		return responseEntity.getBody().isSuccess();
	}

	@Override
	public Product getProduct() {
		// TODO Auto-generated method stub
		return Product.QBPayroll;
	}

	@Override
	public boolean validateProductForUpdate(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {
		// TODO Auto-generated method stub
		log.debug("Calling validate update endpoint with request={} ", updateBusinessProfileRequestDTO);
		UriComponents uri = UriComponentsBuilder.fromUriString(Constants.VALIDATION_URL_BASE)
				.path(Constants.VALIDATION_UPDATE_QB_PAYROLLS_URL_PATH).build();
		HttpEntity<UpdateBusinessProfileRequestDTO> requestEntity = new HttpEntity<>(updateBusinessProfileRequestDTO);
		ResponseEntity<DummyValidatorResponse> responseEntity = restTemplate.exchange(uri.toString(), HttpMethod.POST,
				requestEntity, DummyValidatorResponse.class);
		return responseEntity.getBody().isSuccess();
	}

}
