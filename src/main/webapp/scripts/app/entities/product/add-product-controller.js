'use strict';

angular.module('appstoreApp').controller('AddProduct',
    ['$scope', '$stateParams','Product', 'CompanyInformation',
        function($scope, $stateParams, Product, CompanyInformation) {

        $scope.product = entity;
        $scope.companies = CompanyInformation.query();
        $scope.load = function(id) {
            Product.get({id : id}, function(result) {
                $scope.product = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:productUpdate', result);
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


}]);
