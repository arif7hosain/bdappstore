package com.appstore.repository;

import com.appstore.domain.ProductPortfolio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductPortfolio entity.
 */
public interface ProductPortfolioRepository extends JpaRepository<ProductPortfolio,Long> {

}
