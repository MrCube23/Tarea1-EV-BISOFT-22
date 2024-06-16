package com.project.tarea1Evador.rest.category;

import com.project.tarea1Evador.logic.entity.category.Categoria;
import com.project.tarea1Evador.logic.entity.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class categoryRestController {
    @Autowired
    private CategoryRepository CategoryRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Categoria> getAllCategories() {
        return CategoryRepository.findAll();
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Categoria addCategory(@RequestBody Categoria categories) {
        return CategoryRepository.save(categories);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Categoria getCategoryById(@PathVariable Integer id) {
        return CategoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Categoria updateCategory(@PathVariable Integer id, @RequestBody Categoria category) {
        return CategoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setNombre(category.getNombre());
                    existingCategory.setDescripcion(category.getDescripcion());
                    return CategoryRepository.save(existingCategory);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    return CategoryRepository.save(category);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public void deleteCategory(@PathVariable Integer id) {
        CategoryRepository.deleteById(id);
    }

}