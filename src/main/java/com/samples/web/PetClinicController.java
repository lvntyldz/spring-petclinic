package com.samples.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.samples.service.PetClinicService;

@Controller
public class PetClinicController {

	@Autowired
	private PetClinicService petClinicService;

	@RequestMapping("/owners")
	public ModelAndView getOwners() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("owners", petClinicService.findOwners());
		mv.setViewName("owners");
		return mv;
	}

	@RequestMapping("/welcome")
	@ResponseBody
	public String welcome() {
		return "Welcome to Petclinic";
	}
}
