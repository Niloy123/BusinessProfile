package com.intuit.businessprofile.repository;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.intuit.businessprofile.model.BusinessProfile;

@Repository
@Lazy
public interface BusinessProfileRepository extends MongoRepository<BusinessProfile, String> {

	Optional<BusinessProfile> findById(String id);
}
