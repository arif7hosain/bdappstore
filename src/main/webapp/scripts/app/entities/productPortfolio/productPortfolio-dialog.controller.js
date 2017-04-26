'use strict';

angular.module('appstoreApp').controller('ProductPortfolioDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ProductPortfolio', 'Product',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, ProductPortfolio, Product) {

        $scope.productPortfolio = entity;
        $scope.products = Product.query();
        $scope.load = function(id) {
            ProductPortfolio.get({id : id}, function(result) {
                $scope.productPortfolio = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:productPortfolioUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.productPortfolio.id != null) {
                ProductPortfolio.update($scope.productPortfolio, onSaveSuccess, onSaveError);
            } else {
                ProductPortfolio.save($scope.productPortfolio, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;
        $scope.setImage = function ($file, productPortfolio) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        productPortfolio.image = base64Data;
                        productPortfolio.imageContentType = $file.type;
                    });
                };
            }
        };
}]);
