'use strict';

angular.module('appstoreApp')
    .controller('DistrictDetailController', function ($scope, $rootScope, $stateParams, entity, District, Division) {
        $scope.district = entity;
        $scope.load = function (id) {
            District.get({id: id}, function(result) {
                $scope.district = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:districtUpdate', function(event, result) {
            $scope.district = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
