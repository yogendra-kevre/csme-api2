package com.csme.csmeapi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.csme.csmeapi.api.PetApi;
import com.csme.csmeapi.exception.CustomBodyValidationException;
import com.csme.csmeapi.exception.CustomValidationException;
import com.csme.csmeapi.exception.ErrorCode;
import com.csme.csmeapi.exception.ErrorMessage;
import com.csme.csmeapi.service.CsmeService;
import com.csme.csmeapi.dto.Pet;

@Controller
public class CsmeApiController implements PetApi{

	@Autowired
	private CsmeService csmeService;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	

	@Override
	public ResponseEntity<Pet> addPet(Pet body) throws CustomBodyValidationException {
		 List<String> errorList=validateBody(body);
		if(!errorList.isEmpty())
			throw new CustomBodyValidationException(errorList);
		
		return new ResponseEntity<Pet>(body,HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<Pet> getPetForId(Long petId,String userId) throws CustomValidationException {
		
		if (userId == null)
			throw new CustomValidationException(ErrorCode.CSME_CE_1001); 
		Pet p=csmeService.getPetById(petId);
		return new ResponseEntity<Pet>(p,HttpStatus.OK);
		
	}

	private List<String> validateBody(Pet pet){
		List<String> errorList=new ArrayList<>();
		if(pet.getId()>100)
			errorList.add("Id can not be greater than 100");
		if(pet.getName()==null||pet.getName().isEmpty())
			errorList.add("name can not be empty");
		
		return errorList;
	}
}
