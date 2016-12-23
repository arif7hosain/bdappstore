'use strict';

angular.module('appstoreApp').controller('CreatePublisher',
    ['$scope', '$stateParams',  '$q', 'DataUtils',  'CompanyInformation', 'Country', 'ServiceCategory', 'User',
        function($scope, $stateParams,  $q, DataUtils, CompanyInformation, Country, ServiceCategory, User) {

//        $scope.companyInformation = entity;
//        $scope.countrys = Country.query();
//        $scope.servicecategorys = ServiceCategory.query();
//        $scope.users = User.query();
//        $scope.load = function(id) {
//            CompanyInformation.get({id : id}, function(result) {
//                $scope.companyInformation = result;
//            });
//        };
//
//        var onSaveSuccess = function (result) {
//            $scope.$emit('appstoreApp:companyInformationUpdate', result);
//            $uibModalInstance.close(result);
//            $scope.isSaving = false;
//        };
//
//        var onSaveError = function (result) {
//            $scope.isSaving = false;
//        };
//
//        $scope.save = function () {
//            $scope.isSaving = true;
//            if ($scope.companyInformation.id != null) {
//                CompanyInformation.update($scope.companyInformation, onSaveSuccess, onSaveError);
//            } else {
//                CompanyInformation.save($scope.companyInformation, onSaveSuccess, onSaveError);
//            }
//        };
//
//        $scope.clear = function() {
//            $uibModalInstance.dismiss('cancel');
//        };
//
//        $scope.abbreviate = DataUtils.abbreviate;
//
//        $scope.byteSize = DataUtils.byteSize;
//
//       $scope.setLogo = function ($file, companyInformation) {
//            if ($file) {
//                var fileReader = new FileReader();
//                fileReader.readAsDataURL($file);
//                fileReader.onload = function (e) {
//                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
//                    $scope.$apply(function() {
//                        companyInformation.logo = base64Data;
//                        companyInformation.logoContentType = $file.type;
//                    });
//                };
//            }
//        };
}]);
