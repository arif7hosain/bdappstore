'use strict';

angular.module('appstoreApp').controller('ProductDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Product', 'CompanyInformation', 'ServiceCategory',
        function($scope, $stateParams, $uibModalInstance, entity, Product, CompanyInformation, ServiceCategory) {

        $scope.product = entity;
        $scope.companyinformations = CompanyInformation.query();
        $scope.servicecategorys = ServiceCategory.query();
        $scope.load = function(id) {
            Product.get({id : id}, function(result) {
                $scope.product = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:productUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
        console.log($scope.product);
            $scope.isSaving = true;
            if ($scope.product.id != null) {
                Product.update($scope.product, onSaveSuccess, onSaveError);
            } else {
                Product.save($scope.product, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
