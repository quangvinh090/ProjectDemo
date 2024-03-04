package com.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.Account;
import com.demo.entities.Product;

public interface ProductService {
	public Iterable<Product> findAll();

	public boolean save(Product account);
	
	public boolean delete(int id);
	
	public Product find(int id);

}
