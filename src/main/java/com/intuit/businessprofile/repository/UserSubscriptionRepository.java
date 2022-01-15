package com.intuit.businessprofile.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.intuit.businessprofile.model.UserSubscription;

@Repository
public interface UserSubscriptionRepository extends MongoRepository<UserSubscription, String> {

	Optional<UserSubscription> findById(String userId);

}
