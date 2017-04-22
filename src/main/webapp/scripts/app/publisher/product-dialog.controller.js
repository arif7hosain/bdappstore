'use strict';

angular.module('appstoreApp').controller('AppNew',
    ['$scope','$state', '$stateParams',  'Product', 'CompanyInformation', 'ServiceCategory','SoftwareCategory',
        function($scope, $state, $stateParams,  Product, CompanyInformation, ServiceCategory,SoftwareCategory) {

        if($stateParams.id){
        var product_id=$stateParams.id;
            Product.get({id : product_id}, function(result) {
                $scope.product = result;
            });
        }

        $scope.companyinformations = CompanyInformation.query();
        $scope.servicecategorys = ServiceCategory.query();
        $scope.categories = SoftwareCategory.query();
        console.log($scope.categories);

        var onSaveSuccess = function (result) {
        $state.go('publisher.dashboard');
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
