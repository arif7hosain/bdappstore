package com.appstore.repository;

import com.appstore.domain.District;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the District entity.
 */
public interface DistrictRepository extends JpaRepository<District,Long> {

}
