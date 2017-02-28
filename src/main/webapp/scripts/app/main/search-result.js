'use strict';

angular.module('appstoreApp')
    .controller('ResultController', function ($scope,$state,$stateParams, Principal,SoftwareCategory,Product, ProductSearch) {

     $scope.products = [];
     $scope.types = [];
     $scope.foundSug=false;
     $scope.products = Product.query();
     console.log($scope.products);
     alert();

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
         alert(data);
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
