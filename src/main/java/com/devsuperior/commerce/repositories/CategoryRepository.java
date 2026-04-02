package com.devsuperior.commerce.repositories;

import com.devsuperior.commerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
