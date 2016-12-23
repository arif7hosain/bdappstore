package com.appstore.repository.search;

import com.appstore.domain.ServiceCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ServiceCategory entity.
 */
public interface ServiceCategorySearchRepository extends ElasticsearchRepository<ServiceCategory, Long> {
}
