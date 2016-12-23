'use strict';

angular.module('appstoreApp')
    .factory('ComBranchSearch', function ($resource) {
        return $resource('api/_search/comBranchs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
