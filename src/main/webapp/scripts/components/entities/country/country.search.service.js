'use strict';

angular.module('appstoreApp')
    .factory('CountrySearch', function ($resource) {
        return $resource('api/_search/countrys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
