'use strict';

angular.module('appstoreApp')
	.controller('TempCompanyDeleteController', function($scope, $uibModalInstance, entity, TempCompany) {

        $scope.tempCompany = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TempCompany.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
