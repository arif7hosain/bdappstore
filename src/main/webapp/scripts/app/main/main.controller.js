'use strict';

angular.module('appstoreApp')
    .controller('MainController', function ($scope, Principal,SoftwareCategory) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            $scope.categories=SoftwareCategory.query();
        });
    });
