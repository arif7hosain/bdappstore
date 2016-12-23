'use strict';

angular.module('appstoreApp')
    .factory('ComAddressSearch', function ($resource) {
        return $resource('api/_search/comAddresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
