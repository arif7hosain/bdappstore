'use strict';

angular.module('appstoreApp').controller('AppNew',
    ['$scope', '$stateParams',  'Product', 'CompanyInformation', 'ServiceCategory',
        function($scope, $stateParams,  Product, CompanyInformation, ServiceCategory) {

        if($stateParams.id){
        var product_id=$stateParams.id;
            Product.get({id : product_id}, function(result) {
                $scope.product = result;
            });
        }

        $scope.companyinformations = CompanyInformation.query();
        $scope.servicecategorys = ServiceCategory.query();

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
