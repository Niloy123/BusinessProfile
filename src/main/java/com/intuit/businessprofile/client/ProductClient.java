package com.intuit.businessprofile.client;

import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.enums.Product;

public interface ProductClient {

	boolean validateProduct(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO);

	boolean validateProductForUpdate(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO);

	Product getProduct();
}
