package com.appstore.repository.search;

import com.appstore.domain.TempCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TempCompany entity.
 */
public interface TempCompanySearchRepository extends ElasticsearchRepository<TempCompany, Long> {
}
