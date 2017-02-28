'use strict';
angular.module('appstoreApp').controller('PublisherProfileController',['$scope', '$stateParams','$state', 'DataUtils','Principal','GetCompany',
function($scope, $stateParams, $state,  DataUtils,Principal,GetCompany){

        $scope.company={};
        Principal.identity().then(function(account) {
            $scope.account = account;
            console.log(account);
            $scope.isAuthenticated = Principal.isAuthenticated;
            GetCompany.query({login:account.login},function(company){
            $scope.company=company;
            console.log($scope.company);

            });
        });
}]);
