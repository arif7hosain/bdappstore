package com.appstore.repository;

import com.appstore.domain.ComBranch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ComBranch entity.
 */
public interface ComBranchRepository extends JpaRepository<ComBranch,Long> {

}
