'use strict';

angular.module('appstoreApp')
	.controller('CompanyInformationDeleteController', function($scope, $uibModalInstance, entity, CompanyInformation) {

        $scope.companyInformation = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CompanyInformation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
