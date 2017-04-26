'use strict';

angular.module('appstoreApp')
    .controller('ViewProduct',['$scope','$rootScope','$stateParams','entity','Product','CompanyInformation','ServiceCategory','GetAllProductPortfolio','AddView',
    function ($scope, $rootScope, $stateParams, entity, Product, CompanyInformation, ServiceCategory,GetAllProductPortfolio,AddView) {

        $scope.product = entity;
        $scope.images={};

            Product.get({id: $stateParams.id}, function(result) {
                $scope.product = result;
                GetAllProductPortfolio.query({id: $stateParams.id},function(data){
                $scope.images=data;
                });
            });
         AddView.query({id: $stateParams.id});


    }]);
