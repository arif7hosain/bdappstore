'use strict';

angular.module('appstoreApp')
    .controller('TempCompanyDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, TempCompany, Country, ServiceCategory, Upazila) {
        $scope.tempCompany = entity;
        $scope.load = function (id) {
            TempCompany.get({id: id}, function(result) {
                $scope.tempCompany = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:tempCompanyUpdate', function(event, result) {
            $scope.tempCompany = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
