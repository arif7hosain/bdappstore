'use strict';

angular.module('appstoreApp').controller('ComBranchDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ComBranch', 'CompanyInformation', 'ServiceCategory', 'Country',
        function($scope, $stateParams, $uibModalInstance, entity, ComBranch, CompanyInformation, ServiceCategory, Country) {

        $scope.comBranch = entity;
        $scope.companyinformations = CompanyInformation.query();
        $scope.servicecategorys = ServiceCategory.query();
        $scope.countrys = Country.query();
        console.log('.....country list');
        console.log($scope.countrys);
        $scope.load = function(id) {
            ComBranch.get({id : id}, function(result) {
                $scope.comBranch = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:comBranchUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.comBranch.id != null) {
                ComBranch.update($scope.comBranch, onSaveSuccess, onSaveError);
            } else {
                ComBranch.save($scope.comBranch, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
