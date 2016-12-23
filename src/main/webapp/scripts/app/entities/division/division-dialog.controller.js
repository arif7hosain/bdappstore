'use strict';

angular.module('appstoreApp').controller('DivisionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Division',
        function($scope, $stateParams, $uibModalInstance, entity, Division) {

        $scope.division = entity;
        $scope.load = function(id) {
            Division.get({id : id}, function(result) {
                $scope.division = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:divisionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.division.id != null) {
                Division.update($scope.division, onSaveSuccess, onSaveError);
            } else {
                Division.save($scope.division, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
