'use strict';

angular.module('appstoreApp')
    .controller('ResultController', function ($scope,$state,$stateParams, Principal,SoftwareCategory,Product, ProductSearch,ServiceCategory,SearchApps) {

     $scope.products = [];
     $scope.types = [];
     $scope.foundSug=false;
     $scope.products = Product.query();
     $scope.categories = SoftwareCategory.query();
     $scope.services = ServiceCategory.query();
     $scope.q=$stateParams.q;



        $scope.search = function (q) {
        alert('a');
            if(q){
                SearchApps.query({query:q},function(res){
                $scope.products=res;
                })
            }else{
            $scope.products = Product.query();
            }
        };
        $scope.getAppsByCat=function(id){
            alert(id);
        }
    });
