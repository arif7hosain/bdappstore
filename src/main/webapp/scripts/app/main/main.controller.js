'use strict';

angular.module('appstoreApp')
    .controller('MainController', function ($scope, Principal,SoftwareCategory,Product, ProductSearch) {
//        Principal.identity().then(function(account) {
//            $scope.account = account;
//            $scope.isAuthenticated = Principal.isAuthenticated;
//
//        });

     $scope.products = [];
     $scope.products = Product.query();
     $scope.categories=SoftwareCategory.query();

        $scope.search = function (q) {
            ProductSearch.query({query: q}, function(result) {
                $scope.products = result;
                console.log($scope.products);
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };



    });
