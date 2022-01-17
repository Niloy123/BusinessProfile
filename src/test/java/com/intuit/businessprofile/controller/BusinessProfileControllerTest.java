package com.intuit.businessprofile.controller;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.businessprofile.dto.CreateBusinessProfileRequestDTO;
import com.intuit.businessprofile.dto.UpdateBusinessProfileRequestDTO;
import com.intuit.businessprofile.exceptions.CustomExceptionHandler;
import com.intuit.businessprofile.model.BusinessProfile;
import com.intuit.businessprofile.service.BusinessProfileService;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { BusinessProfileServiceImpl.class })
@WebMvcTest({ BusinessProfileController.class })
public class BusinessProfileControllerTest {

	private MockMvc mvc;

	@MockBean
	private BusinessProfileService businessProfileService;
	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(new BusinessProfileController(businessProfileService))
				.setControllerAdvice(new CustomExceptionHandler()).build();
	}

	@Test
	public void testAddBusinessProfile() throws Exception {
		CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO = random(CreateBusinessProfileRequestDTO.class);

		when(businessProfileService.saveBusinessProfile(createBusinessProfileRequestDTO))
				.thenReturn(random(BusinessProfile.class));

		mvc.perform(post("/v1/businessprofile/createprofile").content(asJsonString(createBusinessProfileRequestDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void testUpdateBusinessProfile() throws Exception {
		UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO = random(UpdateBusinessProfileRequestDTO.class);

		when(businessProfileService.updateBusinessProfile(updateBusinessProfileRequestDTO))
				.thenReturn(random(BusinessProfile.class));

		mvc.perform(
				put("/v1/businessprofile/updateprofile").content(asJsonStringForUpdate(updateBusinessProfileRequestDTO))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	private String asJsonStringForUpdate(UpdateBusinessProfileRequestDTO updateBusinessProfileRequestDTO) {
		// TODO Auto-generated method stub
		try {
			return objectMapper.writeValueAsString(updateBusinessProfileRequestDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String asJsonString(CreateBusinessProfileRequestDTO createBusinessProfileRequestDTO) {
		// TODO Auto-generated method stub
		try {
			return objectMapper.writeValueAsString(createBusinessProfileRequestDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
