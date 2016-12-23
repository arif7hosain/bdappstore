package com.appstore.repository;

import com.appstore.domain.SoftwareCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SoftwareCategory entity.
 */
public interface SoftwareCategoryRepository extends JpaRepository<SoftwareCategory,Long> {

}
