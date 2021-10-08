package com.gokoy.delivery.domain.dao;

import com.gokoy.delivery.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
