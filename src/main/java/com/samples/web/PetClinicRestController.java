package com.samples.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.samples.exception.InternalServerException;
import com.samples.exception.OwnerNotFoundException;
import com.samples.model.Owner;
import com.samples.service.PetClinicService;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {

	@Autowired
	PetClinicService petClinicService;

	@RequestMapping(method = RequestMethod.DELETE, value = "/owner/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id) {
		try {
			petClinicService.deleteOwner(id);
			return ResponseEntity.ok().build();
		} catch (OwnerNotFoundException e) {
			// return ResponseEntity.notFound().build();
			throw e;
		} catch (Exception e) {
			// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			throw new InternalServerException(e);
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/owner/{id}")
	public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner ownerRequest) {
		try {

			Owner owner = petClinicService.findOwner(id);
			owner.setFirstName(ownerRequest.getFirstName());
			owner.setLastName(ownerRequest.getLastName());

			petClinicService.updateOwner(owner);

			return ResponseEntity.ok().build();

		} catch (OwnerNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/owner")
	public ResponseEntity<URI> createOwner(@RequestBody Owner owner) {

		try {
			petClinicService.createOwner(owner);
			Long id = owner.getId();
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

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

	@RequestMapping(method = RequestMethod.GET, value = "/owner/{id}", produces = "application/json")
	public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id) {

		try {

			Owner owner = petClinicService.findOwner(id);

			Link self = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner/" + id).withSelfRel();
			Link create = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner").withRel("create");
			Link update = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner/" + id).withRel("update");
			Link delete = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner/" + id).withRel("delete");

			Resource<Owner> resource = new Resource<Owner>(owner, self, create, update, delete);

			return ResponseEntity.ok(resource);

		} catch (OwnerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
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
