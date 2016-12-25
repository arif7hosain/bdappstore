'use strict';

angular.module('appstoreApp')
    .controller('ProductDetailController', function ($scope, $rootScope, $stateParams, entity, Product, CompanyInformation, ServiceCategory) {
        $scope.product = entity;
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:productUpdate', function(event, result) {
            $scope.product = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
