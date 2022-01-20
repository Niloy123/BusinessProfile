package com.intuit.businessprofile.client;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.enums.Product;

public interface ProductClient {

	boolean validateProduct(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) throws Exception;

	boolean validateProductForUpdate(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) throws Exception;

	Product getProduct();
}
