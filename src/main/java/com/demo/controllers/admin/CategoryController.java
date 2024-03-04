package com.demo.controllers.admin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.demo.entities.Product;

import jakarta.validation.Valid;


@Controller
@RequestMapping({"admin/category", "category", })
public class CategoryController {
	
	@RequestMapping(value = {"index", "", "/"}, method = RequestMethod.GET)
	public String login(ModelMap modelMap) {
		return "admin/category/index";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create() {
		return "admin/product/add";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("product") Product product) {
			
		return "admin/product/add";
		
	}

}
