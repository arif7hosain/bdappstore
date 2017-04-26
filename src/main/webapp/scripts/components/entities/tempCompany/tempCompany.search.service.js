'use strict';

angular.module('appstoreApp')
    .factory('TempCompanySearch', function ($resource) {
        return $resource('api/_search/tempCompanys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('ApproveCompany', function ($resource) {
        return $resource('api/_search/tempCompanys/approveCompany/:id', {}, {
            'query': { method: 'GET', isArray: false}
        });
    })
    .factory('GetPendingCompany', function ($resource) {
        return $resource('api/_search/tempCompanys/Pending', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
