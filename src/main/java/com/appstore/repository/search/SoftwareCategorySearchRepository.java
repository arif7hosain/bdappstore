package com.appstore.repository.search;

import com.appstore.domain.SoftwareCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SoftwareCategory entity.
 */
public interface SoftwareCategorySearchRepository extends ElasticsearchRepository<SoftwareCategory, Long> {
}
