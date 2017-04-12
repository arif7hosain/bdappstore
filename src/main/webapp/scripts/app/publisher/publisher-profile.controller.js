'use strict';
angular.module('appstoreApp')
.controller('PublisherProfileController',['$scope', '$stateParams', 'DataUtils','GetCompany','CompanyInformation', 'Country', 'ServiceCategory', 'User',
function($scope, $stateParams, DataUtils,GetCompany,CompanyInformation, Country, ServiceCategory, User){

        $scope.profileEditable=false;
        $scope.profile=GetCompany.query();
        $scope.editEnable=function(){
         $scope.profileEditable=true;
        };



        $scope.countrys = Country.query();
        $scope.servicecategorys = ServiceCategory.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            CompanyInformation.get({id : id}, function(result) {
                $scope.companyInformation = result;
            });
        };



        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:companyInformationUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
        console.log($scope.profile);
        $scope.profileEditable=false;
            $scope.isSaving = true;
            if ($scope.profile.id != null) {
                CompanyInformation.update($scope.profile, onSaveSuccess, onSaveError);
            } else {
                CompanyInformation.save($scope.profile, onSaveSuccess, onSaveError);
            }
        };

        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;
        $scope.setLogo = function ($file, companyInformation) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        companyInformation.logo = base64Data;
                        companyInformation.logoContentType = $file.type;
                    });
                };
            }
        };

}]);
