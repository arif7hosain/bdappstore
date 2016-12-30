'use strict';

angular.module('appstoreApp')
    .controller('ProductCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, ProductCategory, Product, SoftwareCategory) {
        $scope.productCategory = entity;
        $scope.load = function (id) {
            ProductCategory.get({id: id}, function(result) {
                $scope.productCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:productCategoryUpdate', function(event, result) {
            $scope.productCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
