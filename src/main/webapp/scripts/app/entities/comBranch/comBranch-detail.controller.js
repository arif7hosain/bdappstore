'use strict';

angular.module('appstoreApp')
    .controller('ComBranchDetailController', function ($scope, $rootScope, $stateParams, entity, ComBranch, CompanyInformation, ServiceCategory, Country) {
        $scope.comBranch = entity;
        $scope.load = function (id) {
            ComBranch.get({id: id}, function(result) {
                $scope.comBranch = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:comBranchUpdate', function(event, result) {
            $scope.comBranch = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
