package com.appstore.repository;

import com.appstore.domain.Division;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Division entity.
 */
public interface DivisionRepository extends JpaRepository<Division,Long> {

}
