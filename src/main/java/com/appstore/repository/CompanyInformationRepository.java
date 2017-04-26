package com.appstore.repository;

import com.appstore.domain.CompanyInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CompanyInformation entity.
 */
public interface CompanyInformationRepository extends JpaRepository<CompanyInformation,Long> {

    @Query("select companyInformation from CompanyInformation companyInformation where companyInformation.user.login= :login")
    CompanyInformation getCompampanyByLogin(@Param("login") String login);

    @Query("select companyInformation from CompanyInformation companyInformation where companyInformation.activeStatus= 1")
    List<CompanyInformation> activeCompanies();
}

