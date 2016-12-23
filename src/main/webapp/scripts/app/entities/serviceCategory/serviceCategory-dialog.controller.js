'use strict';

angular.module('appstoreApp').controller('ServiceCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiceCategory',
        function($scope, $stateParams, $uibModalInstance, entity, ServiceCategory) {

        $scope.serviceCategory = entity;
        $scope.load = function(id) {
            ServiceCategory.get({id : id}, function(result) {
                $scope.serviceCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:serviceCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.serviceCategory.id != null) {
                ServiceCategory.update($scope.serviceCategory, onSaveSuccess, onSaveError);
            } else {
                ServiceCategory.save($scope.serviceCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
