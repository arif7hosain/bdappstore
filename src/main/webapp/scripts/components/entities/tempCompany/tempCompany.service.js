'use strict';

angular.module('appstoreApp')
    .factory('TempCompany', function ($resource, DateUtils) {
        return $resource('api/tempCompanys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.CreatedDate = DateUtils.convertLocaleDateFromServer(data.CreatedDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.CreatedDate = DateUtils.convertLocaleDateToServer(data.CreatedDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.CreatedDate = DateUtils.convertLocaleDateToServer(data.CreatedDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
