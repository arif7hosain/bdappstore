'use strict';

angular.module('appstoreApp')
    .controller('ViewProduct',function ($scope, $rootScope, $stateParams, entity, Product, CompanyInformation, ServiceCategory,GetAllProductPortfolio) {
        $scope.product = entity;
        $scope.images=[];
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
            });
        };
        GetAllProductPortfolio.query({id:$scope.product.id},function(results){
            $scope.images=results;
        });



//        var unsubscribe = $rootScope.$on('appstoreApp:productUpdate', function(event, result) {
//            $scope.product = result;
//        });
//        $scope.$on('$destroy', unsubscribe);

    });
