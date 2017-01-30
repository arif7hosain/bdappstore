'use strict';

angular.module('appstoreApp').controller('UserManagementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, User) {

        $scope.user = entity;
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN","ROLE_COMPANY"];
        var onSaveSuccess = function (result) {
            $scope.isSaving = false;
            $uibModalInstance.close(result);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
        console.log($scope.user);
            $scope.isSaving = true;
            if ($scope.user.id != null) {
                User.update($scope.user, onSaveSuccess, onSaveError);
            } else {
                $scope.user.langKey = 'en';
                User.save($scope.user, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
