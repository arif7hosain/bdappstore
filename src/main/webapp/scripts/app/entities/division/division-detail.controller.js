'use strict';

angular.module('appstoreApp')
    .controller('DivisionDetailController', function ($scope, $rootScope, $stateParams, entity, Division) {
        $scope.division = entity;
        $scope.load = function (id) {
            Division.get({id: id}, function(result) {
                $scope.division = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:divisionUpdate', function(event, result) {
            $scope.division = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
