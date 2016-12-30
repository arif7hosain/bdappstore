package com.appstore.repository;

import com.appstore.domain.ProductPortfolio;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductPortfolio entity.
 */
public interface ProductPortfolioRepository extends JpaRepository<ProductPortfolio,Long> {

    @Query("select portfolio from ProductPortfolio portfolio  where portfolio.product.id=:id")
    List<ProductPortfolio> getAllPortfoliosByProduct(@Param("id")Long id);

}
