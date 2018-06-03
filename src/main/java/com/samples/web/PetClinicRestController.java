package com.samples.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samples.exception.OwnerNotFoundException;
import com.samples.model.Owner;
import com.samples.service.PetClinicService;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {

	@Autowired
	PetClinicService petClinicService;

	@RequestMapping(method = RequestMethod.GET, value = "/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		List<Owner> findOwners = petClinicService.findOwners();
		return ResponseEntity.ok(findOwners);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/owner")
	public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastName) {
		List<Owner> findOwners = petClinicService.findOwners(lastName);
		return ResponseEntity.ok(findOwners);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/owner/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {

		try {
			Owner findOwner = petClinicService.findOwner(id);
			return ResponseEntity.ok(findOwner);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

	}

}
