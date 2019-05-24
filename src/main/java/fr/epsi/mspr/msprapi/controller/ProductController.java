package fr.epsi.mspr.msprapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	
	@ApiOperation(value = "Update a product")
	@PostMapping("/product/update")
	public Map<String, String> updateProduct(@Valid @RequestBody Product product) {
		Map<String, String> msg = new HashMap<>();
		if (product.getId() > 0) {
			Optional<Product> form = productRepository.findById((long) product.getId());
			if (form.isPresent()) {
				productRepository.save(product);
				msg.put("success", "Produit modifié");
			} else {
				msg.put("error", "Produit inexistant");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}

	@ApiOperation(value = "Delete a product")
	@PostMapping("/product/delete")
	public Map<String, String> deleteProduct(@Valid @RequestBody Product product) {
		Map<String, String> msg = new HashMap<>();
		if (product.getId() > 0) {
			Optional<Product> form = productRepository.findById((long) product.getId());
			if (form.isPresent()) {
				productRepository.delete(product);
				msg.put("success", "Produit supprimé");
			} else {
				msg.put("error", "Produit inexistant");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
}
