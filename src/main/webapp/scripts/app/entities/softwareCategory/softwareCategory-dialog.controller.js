'use strict';

angular.module('appstoreApp').controller('SoftwareCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SoftwareCategory',
        function($scope, $stateParams, $uibModalInstance, entity, SoftwareCategory) {

        $scope.softwareCategory = entity;
        $scope.load = function(id) {
            SoftwareCategory.get({id : id}, function(result) {
                $scope.softwareCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:softwareCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.softwareCategory.id != null) {
                SoftwareCategory.update($scope.softwareCategory, onSaveSuccess, onSaveError);
            } else {
                SoftwareCategory.save($scope.softwareCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
