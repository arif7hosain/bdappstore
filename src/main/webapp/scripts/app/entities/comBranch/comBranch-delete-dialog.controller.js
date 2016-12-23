'use strict';

angular.module('appstoreApp')
	.controller('ComBranchDeleteController', function($scope, $uibModalInstance, entity, ComBranch) {

        $scope.comBranch = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ComBranch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
