package com.samples.web;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.samples.model.Owner;

public class PetClinicRestControllerTest {

	private RestTemplate restTemplate;

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
	}

	@Test
	public void updateOwner() {
		String url = "http://localhost:8081/petclinic/rest/owner/3";

		Owner owner = restTemplate.getForObject(url, Owner.class);
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Veli"));

		// update
		owner.setLastName("VELİCAN");
		restTemplate.put(url, owner);

		// validate
		owner = restTemplate.getForObject(url, Owner.class);
		MatcherAssert.assertThat(owner.getLastName(), Matchers.equalTo("VELİCAN"));

	}

	@Test
	public void createOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Ali");
		owner.setLastName("ALİZADE");

		URI location = restTemplate.postForLocation("http://localhost:8081/petclinic/rest/owner", owner);
		Owner owner2 = restTemplate.getForObject(location, Owner.class);

		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo(owner2.getFirstName()));
		MatcherAssert.assertThat(owner.getLastName(), Matchers.equalTo(owner2.getLastName()));

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
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Levent", "Ali", "Veli", "Hasan", "Ali"));
	}

	@Test
	public void deleteOwner() {
		String url = "http://localhost:8081/petclinic/rest/owner/14";
		restTemplate.delete(url);
		try {
			restTemplate.getForEntity(url, Owner.class);
			Assert.fail("It should have been deleted!");
		} catch (RestClientException e) {
			// owner not found(delete success)
		}
	}
}
