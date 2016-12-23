package com.appstore.repository;

import com.appstore.domain.ServiceCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceCategory entity.
 */
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {

}
