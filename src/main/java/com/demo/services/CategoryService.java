package com.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.Account;
import com.demo.entities.Category;
import com.demo.entities.Product;

public interface CategoryService {
	public Iterable<Category> findAll();

}
