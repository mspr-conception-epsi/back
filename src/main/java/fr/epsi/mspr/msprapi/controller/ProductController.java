package fr.epsi.mspr.msprapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.mspr.msprapi.entities.Product;
import fr.epsi.mspr.msprapi.repository.ProductRepository;
import io.swagger.annotations.ApiOperation;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@ApiOperation(value = "List of product")
	@GetMapping("/product")
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	@ApiOperation(value = "Create new product")
	@PostMapping("/product/create")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }
	
	@ApiOperation(value = "Delte a product")
	@PostMapping("/product/delete")
    public Product deleteProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }
}
