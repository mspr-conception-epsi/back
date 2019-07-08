package fr.epsi.mspr.msprapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.epsi.mspr.msprapi.entities.Product;
import fr.epsi.mspr.msprapi.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTests {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void addAndRemoveProduct() {
		Product product = new Product();
		try {
			product.setName("junit test");
			product.setPrice(1000000);
			Product createdProduct = productRepository.save(product);
			productRepository.delete(createdProduct);
			assertEquals(product.getName(), createdProduct.getName());
		} catch(Exception ex) {
			System.out.println(ex);
			productRepository.delete(product);
		}
	}
	
	@Test
	public void addEditAndRemoveProduct() {
		Product product = new Product();
		Product createdProduct = null;
		try {
			product.setName("junit test");
			product.setPrice(1000000);
			createdProduct = productRepository.save(product);
			createdProduct.setPrice(20);
			Product createdProductEdit = productRepository.save(createdProduct);
			productRepository.delete(createdProduct);
			productRepository.delete(createdProductEdit);
			assertTrue(createdProduct.getPrice() == createdProductEdit.getPrice());
		} catch(Exception ex) {
			System.out.println(ex);
			productRepository.delete(product);
			productRepository.delete(createdProduct);
		}
	}

}
