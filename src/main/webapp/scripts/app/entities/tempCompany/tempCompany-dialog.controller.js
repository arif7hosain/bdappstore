'use strict';

angular.module('appstoreApp').controller('TempCompanyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'TempCompany', 'Country', 'ServiceCategory', 'Upazila',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, TempCompany, Country, ServiceCategory, Upazila) {

        $scope.tempCompany = entity;
        $scope.countrys = Country.query();
        $scope.servicecategorys = ServiceCategory.query();
        $scope.upazilas = Upazila.query();
        $scope.load = function(id) {
            TempCompany.get({id : id}, function(result) {
                $scope.tempCompany = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:tempCompanyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.tempCompany.id != null) {
                TempCompany.update($scope.tempCompany, onSaveSuccess, onSaveError);
            } else {
                TempCompany.save($scope.tempCompany, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setLogo = function ($file, tempCompany) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        tempCompany.logo = base64Data;
                        tempCompany.logoContentType = $file.type;
                    });
                };
            }
        };
}]);
