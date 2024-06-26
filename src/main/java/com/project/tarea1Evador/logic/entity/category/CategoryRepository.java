package com.project.tarea1Evador.logic.entity.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Categoria, Integer>, CrudRepository<Categoria, Integer> {
}