'use strict';

angular.module('appstoreApp')
    .factory('DistrictSearch', function ($resource) {
        return $resource('api/_search/districts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
