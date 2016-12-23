package com.appstore.repository.search;

import com.appstore.domain.CompanyInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CompanyInformation entity.
 */
public interface CompanyInformationSearchRepository extends ElasticsearchRepository<CompanyInformation, Long> {
}
