'use strict';

angular.module('appstoreApp')
    .controller('ComAddressDetailController', function ($scope, $rootScope, $stateParams, entity, ComAddress, ComBranch, Upazila) {
        $scope.comAddress = entity;
        $scope.load = function (id) {
            ComAddress.get({id: id}, function(result) {
                $scope.comAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:comAddressUpdate', function(event, result) {
            $scope.comAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
