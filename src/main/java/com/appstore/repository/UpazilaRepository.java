package com.appstore.repository;

import com.appstore.domain.Upazila;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Upazila entity.
 */
public interface UpazilaRepository extends JpaRepository<Upazila,Long> {

}
