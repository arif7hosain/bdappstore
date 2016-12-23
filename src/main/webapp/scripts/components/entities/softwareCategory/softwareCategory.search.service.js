'use strict';

angular.module('appstoreApp')
    .factory('SoftwareCategorySearch', function ($resource) {
        return $resource('api/_search/softwareCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
