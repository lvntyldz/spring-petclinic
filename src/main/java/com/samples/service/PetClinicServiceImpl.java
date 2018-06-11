package com.samples.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samples.dao.OwnerRepository;
import com.samples.dao.PetRepository;
import com.samples.exception.OwnerNotFoundException;
import com.samples.model.Owner;

@Service
@Transactional
public class PetClinicServiceImpl implements PetClinicService {

	private OwnerRepository ownerRepository;
	private PetRepository petRepository;

	@Autowired
	public void setOwnerRepository(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Autowired
	public void setPetRepository(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	public Owner findOwner(Long id) throws OwnerNotFoundException {

		Owner owner = ownerRepository.findById(id);
		if (owner == null)
			throw new OwnerNotFoundException("Owner Not Found with Given Id : " + id);
		return owner;
	}

	@Override
	public void createOwner(Owner owner) {
		ownerRepository.create(owner);
	}

	@Override
	public void updateOwner(Owner owner) {
		ownerRepository.update(owner);
	}

	@Override
	public void deleteOwner(Long id) {
		petRepository.deleteByOwnerId(id);
		ownerRepository.delete(id);
	}

}
