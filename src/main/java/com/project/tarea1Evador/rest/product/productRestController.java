package com.project.tarea1Evador.rest.product;

import com.project.tarea1Evador.logic.entity.category.Categoria;
import com.project.tarea1Evador.logic.entity.category.CategoryRepository;
import com.project.tarea1Evador.logic.entity.product.Producto;
import com.project.tarea1Evador.logic.entity.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class productRestController {
    @Autowired
    private ProductRepository ProductRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Producto> getAllProducts() {
        return ProductRepository.findAll();
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Producto addProduct(@RequestBody Producto product) {
        Optional<Categoria> optionalCategory = categoryRepository.findById(product.getCategoria().getId());
        if (optionalCategory.isEmpty()) {
            return null;
        }
        product.setCategoria(optionalCategory.get());
        return ProductRepository.save(product);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Producto getProductById(@PathVariable Long id) {
        return ProductRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    @PutMapping("/{id}")
    public Producto updateProduct(@PathVariable Long id, @RequestBody Producto product) {
        return ProductRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setNombre(product.getNombre());
                    existingProduct.setDescripcion(product.getDescripcion());
                    existingProduct.setPrecio(product.getPrecio());
                    existingProduct.setCantidad_en_stock(product.getCantidad_en_stock());
                    Optional<Categoria> optionalCategory = categoryRepository.findById(product.getCategoria().getId());
                    existingProduct.setCategoria(optionalCategory.get());
                    return ProductRepository.save(existingProduct);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return ProductRepository.save(product);
                });
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        ProductRepository.deleteById(id);
    }

}