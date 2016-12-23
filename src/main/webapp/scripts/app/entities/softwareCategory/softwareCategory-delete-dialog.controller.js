'use strict';

angular.module('appstoreApp')
	.controller('SoftwareCategoryDeleteController', function($scope, $uibModalInstance, entity, SoftwareCategory) {

        $scope.softwareCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SoftwareCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
