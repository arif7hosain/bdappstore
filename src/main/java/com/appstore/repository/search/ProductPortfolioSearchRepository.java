package com.appstore.repository.search;

import com.appstore.domain.ProductPortfolio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductPortfolio entity.
 */
public interface ProductPortfolioSearchRepository extends ElasticsearchRepository<ProductPortfolio, Long> {
}
