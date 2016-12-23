package com.appstore.repository;

import com.appstore.domain.ComAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ComAddress entity.
 */
public interface ComAddressRepository extends JpaRepository<ComAddress,Long> {

}
