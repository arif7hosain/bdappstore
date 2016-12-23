'use strict';

angular.module('appstoreApp')
    .factory('ServiceCategorySearch', function ($resource) {
        return $resource('api/_search/serviceCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
