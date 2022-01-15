package com.intuit.businessprofile.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.model.BusinessProfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Lazy
public class BusinessProfileCustomRepositoryImpl implements BusinessProfileCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public BusinessProfile update(BusinessProfile businessProfile) {
		// TODO Auto-generated method stub
		return mongoTemplate.save(businessProfile);
	}

}
