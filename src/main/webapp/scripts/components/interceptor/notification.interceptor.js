 'use strict';

angular.module('appstoreApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-appstoreApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-appstoreApp-params')});
                }
                return response;
            }
        };
    });
