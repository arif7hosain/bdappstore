'use strict';

angular.module('appstoreApp')
    .controller('UpazilaDetailController', function ($scope, $rootScope, $stateParams, entity, Upazila, District) {
        $scope.upazila = entity;
        $scope.load = function (id) {
            Upazila.get({id: id}, function(result) {
                $scope.upazila = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:upazilaUpdate', function(event, result) {
            $scope.upazila = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
