package com.appstore.repository;

import com.appstore.domain.ProductCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductCategory entity.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

}
