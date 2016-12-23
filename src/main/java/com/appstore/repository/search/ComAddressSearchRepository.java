package com.appstore.repository.search;

import com.appstore.domain.ComAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ComAddress entity.
 */
public interface ComAddressSearchRepository extends ElasticsearchRepository<ComAddress, Long> {
}
