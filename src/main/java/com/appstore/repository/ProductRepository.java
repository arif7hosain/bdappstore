package com.appstore.repository;

import com.appstore.domain.CompanyInformation;
import com.appstore.domain.Product;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("select company from CompanyInformation company where company.user.id = :id")
    CompanyInformation getCompany(@Param("id") Long id);


    @Query("select product from Product product where product.companyInformation.id = :id")
    List<Product> getPublisherApps(@Param("id") Long id);

}
