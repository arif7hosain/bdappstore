package com.appstore.repository;

import com.appstore.domain.Product;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product,Long> {

//    @Query("product as product for product in products where")

}
