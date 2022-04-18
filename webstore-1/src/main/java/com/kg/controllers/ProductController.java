package com.kg.controllers;

import java.util.*;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kg.domain.Product;
import com.kg.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/products")
	public String list(Model model) {
		List<Product> products = productService.findAllProducts();
		model.addAttribute("products", products);
		return "product";
	}
	
	@RequestMapping("/products/{category}")
	public String listProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		model.addAttribute("products", productService.getListOfProductsByCategory(productCategory));
		return "product";
	}
	
	@RequestMapping("/product")
	public String getProductById(Model model, @RequestParam String id) {
		model.addAttribute("product", productService.getProductByProductid(id));
		System.out.println("get method called");
		return "singleProduct";
	}
	@RequestMapping(value="/products/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}
	@RequestMapping(value="/products/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct,
			BindingResult result) {
		System.out.println("new product added has order:  "+newProduct.getUnitsInOrder());
		String[] supressedFields = result.getSuppressedFields();
		if(supressedFields.length>0) {
			throw new RuntimeException("Attemping to bind disallowed fields");
		}
		long test = newProduct.getUnitsInStock() + 100L;
		System.out.println(test);
		productService.saveProduct(newProduct);
		return "redirect:/products";
	}
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("productId",
				"name",
				"unitPrice",
				"description",
				"manufacturer",
				"category",
				"unitsInStock",
				"conditional");
		
	}
}
