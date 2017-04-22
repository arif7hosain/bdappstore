'use strict';

angular.module('appstoreApp')
    .controller('ResultController', function ($scope,$state,$stateParams, Principal,SoftwareCategory,Product, ProductSearch,ServiceCategory) {

     $scope.products = [];
     $scope.types = [];
     $scope.foundSug=false;
     $scope.products = Product.query();
     $scope.categories = SoftwareCategory.query();
     $scope.services = ServiceCategory.query();
     $scope.q=$stateParams.q;

        $scope.search = function (q) {
        if(q){
            ProductSearch.query({query: q}, function(result) {
                $scope.products = result;
                console.log($scope.products);
                console.log($scope.products.length);
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
            }else{
            alert('');
           $scope.products = Product.query();
            }
        };
    });
