package com.intuit.businessprofile.controller;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.businessprofile.dto.GetSubscriptionDTO;
import com.intuit.businessprofile.dto.UserSubscriptionRequestDTO;
import com.intuit.businessprofile.model.UserSubscription;
import com.intuit.businessprofile.service.UserSubscriptionService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest({ UserSubscriptionController.class })
public class UserSubscriptionControllerTest {

	private MockMvc mvc;

	@MockBean
	private UserSubscriptionService userSubscriptionService;
	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(new UserSubscriptionController(userSubscriptionService)).build();
	}

	@Test
	public void testAddSubscription() throws Exception {
		UserSubscriptionRequestDTO userSubscriptionRequestDTO = random(UserSubscriptionRequestDTO.class);

		when(userSubscriptionService.saveUserScription(userSubscriptionRequestDTO))
				.thenReturn(random(UserSubscription.class));

		mvc.perform(post("/v1/usersubscription/addSubscription").content(asJsonString(userSubscriptionRequestDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void testGetSubscription() throws Exception {

		GetSubscriptionDTO getSubscriptionDTO = random(GetSubscriptionDTO.class);
		when(userSubscriptionService.getSubscription("1234")).thenReturn(getSubscriptionDTO);

		mvc.perform(get("/v1/usersubscription/getSubscription/1234")).andExpect(status().isOk());

	}

	private String asJsonString(UserSubscriptionRequestDTO userSubscriptionRequestDTO) {
		// TODO Auto-generated method stub
		try {
			return objectMapper.writeValueAsString(userSubscriptionRequestDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
