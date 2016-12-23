'use strict';

angular.module('appstoreApp')
	.controller('ComAddressDeleteController', function($scope, $uibModalInstance, entity, ComAddress) {

        $scope.comAddress = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ComAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
