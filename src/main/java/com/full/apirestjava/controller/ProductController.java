package com.full.apirestjava.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.full.apirestjava.exception.ResourceNotFoundException;
import com.full.apirestjava.model.Product;
import com.full.apirestjava.repository.ProductRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/")
@Api(value="API REST Produtos")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	//get all products
	@ApiOperation(value="Retorna uma lista de Produtos")
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	// create products rest api
	@ApiOperation(value="Cria um produto")
	@PostMapping("/products")
	public Product createProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	// get products by id rest api
	@ApiOperation(value="Retorna um produto unico")
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductsById(@PathVariable Long id) {
		Product products = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not exist with id :" + id));
		return ResponseEntity.ok(products);
	}
	
	// update product rest api
	@ApiOperation(value="Atualiza um produto")
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not exist with id :" + id));
			
		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		product.setPrice(productDetails.getPrice());
		product.setAmount(productDetails.getAmount());
			
		Product updateProduct = productRepository.save(product);
		return ResponseEntity.ok(updateProduct);
	}
	
	// delete product rest api
	@ApiOperation(value="Deleta um produto")
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not exist with id :" + id));
			
		productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}	
}
