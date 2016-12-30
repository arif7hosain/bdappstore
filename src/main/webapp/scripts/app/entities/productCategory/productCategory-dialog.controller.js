'use strict';

angular.module('appstoreApp').controller('ProductCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductCategory', 'Product', 'SoftwareCategory',
        function($scope, $stateParams, $uibModalInstance, entity, ProductCategory, Product, SoftwareCategory) {

        $scope.productCategory = entity;
        $scope.products = Product.query();
        $scope.softwarecategorys = SoftwareCategory.query();
        $scope.load = function(id) {
            ProductCategory.get({id : id}, function(result) {
                $scope.productCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:productCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.productCategory.id != null) {
                ProductCategory.update($scope.productCategory, onSaveSuccess, onSaveError);
            } else {
                ProductCategory.save($scope.productCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
