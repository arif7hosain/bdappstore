'use strict';

angular.module('appstoreApp')
    .factory('DivisionSearch', function ($resource) {
        return $resource('api/_search/divisions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
