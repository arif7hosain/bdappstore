'use strict';

angular.module('appstoreApp')
    .factory('ProductSearch', function ($resource) {
        return $resource('api/_search/products/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('GetAppsByCompany', function ($resource) {
        return $resource('api/_search/products/currentUse', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('AddView', function ($resource) {
        return $resource('api/_search/products/addView/:id', {}, {
            'query': { method: 'GET', isArray: false}
        });
    });
