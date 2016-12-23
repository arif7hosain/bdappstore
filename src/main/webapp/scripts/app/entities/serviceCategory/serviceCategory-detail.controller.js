'use strict';

angular.module('appstoreApp')
    .controller('ServiceCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, ServiceCategory) {
        $scope.serviceCategory = entity;
        $scope.load = function (id) {
            ServiceCategory.get({id: id}, function(result) {
                $scope.serviceCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:serviceCategoryUpdate', function(event, result) {
            $scope.serviceCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
