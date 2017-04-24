'use strict';

angular.module('appstoreApp').controller('AppNew',
    ['$scope','$state', '$stateParams',  'Product', 'CompanyInformation', 'ServiceCategory','SoftwareCategory','ProductPortfolio','DataUtils',
        function($scope, $state, $stateParams,  Product, CompanyInformation, ServiceCategory,SoftwareCategory,ProductPortfolio,DataUtils) {

        if($stateParams.id){
        var product_id=$stateParams.id;
            Product.get({id : product_id}, function(result) {
                $scope.product = result;
            });
        }
        $scope.appPics=[];
        $scope.appPic={};
        $scope.productPortfolio = {};

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
