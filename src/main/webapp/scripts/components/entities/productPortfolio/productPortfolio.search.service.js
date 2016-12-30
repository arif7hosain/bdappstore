'use strict';

angular.module('appstoreApp')
    .factory('ProductPortfolioSearch', function ($resource) {
        return $resource('api/_search/productPortfolios/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
