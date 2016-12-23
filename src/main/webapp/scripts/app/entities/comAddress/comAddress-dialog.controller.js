'use strict';

angular.module('appstoreApp').controller('ComAddressDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ComAddress', 'ComBranch', 'Upazila',
        function($scope, $stateParams, $uibModalInstance, entity, ComAddress, ComBranch, Upazila) {

        $scope.comAddress = entity;
        $scope.combranchs = ComBranch.query();
        $scope.upazilas = Upazila.query();
        $scope.load = function(id) {
            ComAddress.get({id : id}, function(result) {
                $scope.comAddress = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:comAddressUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.comAddress.id != null) {
                ComAddress.update($scope.comAddress, onSaveSuccess, onSaveError);
            } else {
                ComAddress.save($scope.comAddress, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
