'use strict';

angular.module('appstoreApp')
    .controller('MainController', function ($scope, Principal,SoftwareCategory,Product, ProductSearch) {
//        Principal.identity().then(function(account) {
//            $scope.account = account;
//            $scope.isAuthenticated = Principal.isAuthenticated;
//
//        });

     $scope.products = [];
     $scope.types = [];
     $scope.foundSug=false;
     $scope.products = Product.query();
     $scope.categories=SoftwareCategory.query();

        $scope.search = function (q) {
            ProductSearch.query({query: q}, function(result) {
                $scope.products = result;
                console.log($scope.products.length);
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };
     $scope.getProducts=function(data){

     };

     $scope.type=function(q){
        ProductSearch.query({query: q}, function(result) {
            $scope.types = result;
             $scope.foundSug=true;
            console.log($scope.products.length);
        }, function(response) {
            if(response.status === 404) {
                $scope.loadAll();
            }
        });
     };

    });
