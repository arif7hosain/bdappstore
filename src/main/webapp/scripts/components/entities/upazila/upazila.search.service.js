'use strict';

angular.module('appstoreApp')
    .factory('UpazilaSearch', function ($resource) {
        return $resource('api/_search/upazilas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
