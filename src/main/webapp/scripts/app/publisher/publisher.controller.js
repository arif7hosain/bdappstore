'use strict';

angular.module('appstoreApp').controller('CreatePublisher',
    ['$scope', '$stateParams', 'DataUtils', 'TempCompany', 'Country', 'ServiceCategory', 'Upazila','Auth','$timeout',
        function($scope, $stateParams,  DataUtils,  TempCompany, Country, ServiceCategory, Upazila,Auth,$timeout) {

             $scope.registerAccount = {};
             $scope.tempCompany = {};
              $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});
            $scope.register = function () {
                if ($scope.registerAccount.password !== $scope.confirmPassword) {
                    $scope.doNotMatch = 'ERROR';
                } else {
                    $scope.registerAccount.langKey =  'en' ;
                    $scope.doNotMatch = null;
                    $scope.error = null;
                    $scope.errorUserExists = null;
                    $scope.errorEmailExists = null;

                    Auth.createAccount($scope.registerAccount).then(function () {
                        $scope.success = 'OK';

                    }).catch(function (response) {
                        $scope.success = null;
                        if (response.status === 400 && response.data === 'login already in use') {
                            $scope.errorUserExists = 'ERROR';
                        } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                            $scope.errorEmailExists = 'ERROR';
                        } else {
                            $scope.error = 'ERROR';
                        }
                    });
                }
            };
            $scope.countries = Country.query();
            $scope.servicecategorys = ServiceCategory.query();
            $scope.upazilas = Upazila.query();

            var onSaveSuccess = function (result) {
                $scope.$emit('appstoreApp:tempCompanyUpdate', result);

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



        $scope.tempCompany.companyName;
        $scope.tempCompany.shortName;
        $scope.tempCompany.username;
        $scope.tempCompany.email;
        $scope.tempCompany.password;
        $scope.tempCompany.branchName;
        $scope.tempCompany.BranchType;
        $scope.tempCompany.companyName;
        $scope.tempCompany.shortName;
        $scope.tempCompany.companyInformation;
        $scope.tempCompany.businessDescription;
//        $scope.tempCompany.tempCompany.shortDescription;
//        $scope.tempCompany.country.id;
        $scope.tempCompany.city;
        $scope.tempCompany.postalCode;
        $scope.tempCompany.house;
        $scope.tempCompany.RoadNo;
        $scope.tempCompany.logo;
        $scope.tempCompany.logoContentType;





        $scope.create_publisher_account=function(){
            $scope.tempCompany.username=$scope.registerAccount.login;
            $scope.tempCompany.email=$scope.registerAccount.email;
            $scope.tempCompany.password=$scope.registerAccount.password;

            console.log('..............');
            console.log($scope.tempCompany);
            if($scope.tempCompany){
                $scope.save();//save company
            }else{
                alert('Fill all required fields');
            }
        };

}]);
