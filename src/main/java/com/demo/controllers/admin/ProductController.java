package com.demo.controllers.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entities.Product;
import com.demo.helpers.FileHelper;
import com.demo.services.CategoryService;
import com.demo.services.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({ "admin/product", "product", })
public class ProductController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("products", productService.findAll());
		return "admin/product/index";
	}
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, ModelMap modelMap) {
		modelMap.put("product", productService.find(id));
		return "admin/product/detail";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(ModelMap modelMap) {
		modelMap.put("categories", categoryService.findAll());
		modelMap.put("product", new Product());
		return "admin/product/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && file.getSize() > 0) {

				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/assets/admin/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				product.setPicture(fileName);
				product.setStatus("In stock");
			} else {
				product.setPicture("no-image.jpg");
			}
			if (productService.save(product)) {
				return "redirect:/admin/product/index";
			} else {
				redirectAttributes.addFlashAttribute("msg", "Failed");
				return "redirect:/admin/product/add";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/product/index";
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, ModelMap modelMap) {
			modelMap.put("categories", categoryService.findAll());
			modelMap.put("product", productService.find(id));
			return "admin/product/edit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("product") Product product, 
			RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file) {
		Product product_last = productService.find(product.getId());
		try {
			if (file != null && file.getSize() > 0) {

				File folderImage = new File(new ClassPathResource(".").getFile().getPath() + "/static/assets/admin/images");
				String fileName = FileHelper.generateFileName(file.getOriginalFilename());
				Path path = Paths.get(folderImage.getAbsolutePath() + File.separator + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				product.setPicture(fileName);
				product.setStatus("In stock");
			} else {
				product.setPicture(product_last.getPicture());
			}
			product.setStatus(product_last.getStatus());
			if (productService.save(product)) {
				return "redirect:/admin/product/index";
			} else {
				redirectAttributes.addFlashAttribute("msg", "Failed");
				return "redirect:/admin/product/edit";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/product/index";
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		if (productService.delete(id)) {
			redirectAttributes.addFlashAttribute("msg", "Success");
		} else {
			redirectAttributes.addFlashAttribute("msg", "Fails");
		}
		return "redirect:/admin/product/index";
	}
	

}
