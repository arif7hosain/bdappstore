package com.appstore.repository.search;

import com.appstore.domain.Division;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Division entity.
 */
public interface DivisionSearchRepository extends ElasticsearchRepository<Division, Long> {
}
