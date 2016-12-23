'use strict';

angular.module('appstoreApp')
	.controller('ServiceCategoryDeleteController', function($scope, $uibModalInstance, entity, ServiceCategory) {

        $scope.serviceCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ServiceCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
