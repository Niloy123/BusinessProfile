package com.intuit.businessprofile.mappers;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.intuit.businessprofile.dto.GetBusinessProfileDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.model.BusinessProfile;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BusinessProfileMappers.class })
public class BusinessProfileMappersTest {

	private BusinessProfileMappers businessProfileMappers;

	@BeforeEach
	public void setup() {
		businessProfileMappers = new BusinessProfileMappers();
	}

	@Test
	public void testToBusinessProfileDTO() {
		BusinessProfile businessProfile = random(BusinessProfile.class);
		businessProfile.setId("1234");
		GetBusinessProfileDTO getBusinessProfileDTO = businessProfileMappers.toBusinessProfileDTO("1234",
				businessProfile);
		assertEquals(getBusinessProfileDTO.getCompanyId(), businessProfile.getCompanyId());
		assertEquals(getBusinessProfileDTO.getUserId(), businessProfile.getId());
	}

	@Test
	public void testUpdateBusinessRequest() {
		BusinessProfile businessProfile = random(BusinessProfile.class);
		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);

		BusinessProfile actual = businessProfileMappers.updateBusinessProfile(businessProfile,
				updateBusinessProfileRequestDTO);
		assertEquals(actual.getCompanyId(), updateBusinessProfileRequestDTO.getCompanyId());
	}
}
