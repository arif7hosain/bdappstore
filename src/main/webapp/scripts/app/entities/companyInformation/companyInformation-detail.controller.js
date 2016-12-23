'use strict';

angular.module('appstoreApp')
    .controller('CompanyInformationDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, CompanyInformation, Country, ServiceCategory, User) {
        $scope.companyInformation = entity;
        $scope.load = function (id) {
            CompanyInformation.get({id: id}, function(result) {
                $scope.companyInformation = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:companyInformationUpdate', function(event, result) {
            $scope.companyInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
