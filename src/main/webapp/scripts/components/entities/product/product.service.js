'use strict';

angular.module('appstoreApp')
    .factory('Product', function ($resource, DateUtils) {
        return $resource('api/products/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
