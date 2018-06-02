package com.samples.service;

import java.util.List;

import com.samples.exception.OwnerNotFoundException;
import com.samples.model.Owner;

public interface PetClinicService {

	List<Owner> findOwners();

	List<Owner> findOwners(String lastName);

	Owner findOwner(Long id) throws OwnerNotFoundException;

	void createOwner(Owner owner);

	void updateOwner(Owner owner);

	void deleteOwner(Long id);

}
