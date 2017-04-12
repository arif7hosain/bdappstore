'use strict';

angular.module('appstoreApp')
    .factory('CompanyInformationSearch', function ($resource) {
        return $resource('api/_search/companyInformations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('GetCompany', function ($resource) {
        return $resource('api/_search/companyInformations/info', {}, {
            'query': { method: 'GET', isArray: false}
        });
    });
