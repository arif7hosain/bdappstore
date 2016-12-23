'use strict';

angular.module('appstoreApp')
	.controller('UpazilaDeleteController', function($scope, $uibModalInstance, entity, Upazila) {

        $scope.upazila = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Upazila.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
