'use strict';

angular.module('appstoreApp')
    .factory('TempCompanySearch', function ($resource) {
        return $resource('api/_search/tempCompanys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
