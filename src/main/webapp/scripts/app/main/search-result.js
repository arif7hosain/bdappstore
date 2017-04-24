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

//     $scope.loadAll = function() {
//     alert('hi')
//          Product.query({page: $scope.page - 1, size: 30, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
//                 $scope.links = ParseLinks.parse(headers('link'));
//                 $scope.totalItems = headers('X-Total-Count');
//                 $scope.products = result;
//                 console.log('>pag.')
//                 console.log(result)
//             });
//     }

        $scope.search = function (q) {
//         $scope.loadAll();
            if(q){
                SearchApps.query({query:q},function(res){
                $scope.products=res;
                })
            }else{
            $scope.products = Product.query();
            }
        };

    });



//        $scope.search = function () {
//            TempCompanySearch.query({query: $scope.searchQuery}, function(result) {
//                $scope.tempCompanys = result;
//            }, function(response) {
//                if(response.status === 404) {
//                    $scope.loadAll();
//                }
//            });
//        };
