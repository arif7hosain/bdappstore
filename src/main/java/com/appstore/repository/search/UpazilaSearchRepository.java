package com.appstore.repository.search;

import com.appstore.domain.Upazila;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Upazila entity.
 */
public interface UpazilaSearchRepository extends ElasticsearchRepository<Upazila, Long> {
}
