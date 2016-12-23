package com.appstore.repository;

import com.appstore.domain.CompanyInformation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CompanyInformation entity.
 */
public interface CompanyInformationRepository extends JpaRepository<CompanyInformation,Long> {

}
