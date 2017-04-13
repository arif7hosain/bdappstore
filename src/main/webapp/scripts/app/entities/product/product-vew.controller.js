'use strict';

angular.module('appstoreApp')
    .controller('ViewProduct',['$scope','$rootScope','$stateParams','entity','Product','CompanyInformation','ServiceCategory','GetAllProductPortfolio','AddView',
    function ($scope, $rootScope, $stateParams, entity, Product, CompanyInformation, ServiceCategory,GetAllProductPortfolio,AddView) {
        $scope.product = entity;
        $scope.images=[];
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
            });
        };

        GetAllProductPortfolio.query({id: $stateParams.id},function(results){
            $scope.images=results;
        });

         AddView.query({id: $scope.product.id});

    }]);
