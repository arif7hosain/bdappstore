'use strict';

angular.module('appstoreApp').controller('CreatePublisher',
    ['$scope', '$stateParams',  '$q', 'DataUtils', '$timeout', 'CompanyInformation', 'Country', 'ServiceCategory', 'User','Auth',
        function($scope, $stateParams,  $q, DataUtils,$timeout, CompanyInformation, Country, ServiceCategory, User,Auth) {

        $scope.countries=Country.query();
          $scope.registerAccount = {};
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





        $scope.create_publisher_account=function(){
            $scope.register();//create credentials
//            $scope.create_publisher_info();//create details about publisher
//            $scope.create_branch();//publisher branch information
//            $scope.create_publisher_address();//address about publisher
//            $state.go('/home');
        };

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
