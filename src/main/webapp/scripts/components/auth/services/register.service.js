'use strict';

angular.module('appstoreApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


