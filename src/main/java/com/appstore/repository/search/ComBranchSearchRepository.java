package com.appstore.repository.search;

import com.appstore.domain.ComBranch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ComBranch entity.
 */
public interface ComBranchSearchRepository extends ElasticsearchRepository<ComBranch, Long> {
}
