package com.appstore.repository;

import com.appstore.domain.TempCompany;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TempCompany entity.
 */
public interface TempCompanyRepository extends JpaRepository<TempCompany,Long> {

    @Query("select temp from TempCompany temp where temp.activeStatus = 0 ")
    List<TempCompany> getPendingRequest();

}
