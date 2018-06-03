package com.samples.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.samples.model.Owner;

public class PetClinicRestControllerTest {

	private RestTemplate restTemplate;

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
	}

	@Test
	public void getOwnerById() {
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8081/petclinic/rest/owner/1", Owner.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Levent"));
	}

	@Test
	public void getOwnersByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/petclinic/rest/owner?ln=YILDIZ", List.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

		List<Map<String, String>> body = response.getBody();
		List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Levent"));
	}

	@Test
	public void getOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/petclinic/rest/owners", List.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

		List<Map<String, String>> body = response.getBody();
		List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Levent", "Ali", "Veli", "Hasan"));
	}

}
