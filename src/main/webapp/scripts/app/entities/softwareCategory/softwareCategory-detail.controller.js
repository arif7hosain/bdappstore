'use strict';

angular.module('appstoreApp')
    .controller('SoftwareCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, SoftwareCategory) {
        $scope.softwareCategory = entity;
        $scope.load = function (id) {
            SoftwareCategory.get({id: id}, function(result) {
                $scope.softwareCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:softwareCategoryUpdate', function(event, result) {
            $scope.softwareCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
