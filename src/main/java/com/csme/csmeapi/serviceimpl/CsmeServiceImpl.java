package com.csme.csmeapi.serviceimpl;

import org.springframework.stereotype.Service;

import com.csme.csmeapi.dto.Pet;
import com.csme.csmeapi.service.CsmeService;

@Service
public class CsmeServiceImpl implements CsmeService {

	@Override
	public Pet getPetById(Long id) {
		
		Pet pet = new Pet();
		pet.setId(id);
		return pet;
		
	}
}
