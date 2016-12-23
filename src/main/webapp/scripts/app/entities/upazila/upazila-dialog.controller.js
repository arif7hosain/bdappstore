'use strict';

angular.module('appstoreApp').controller('UpazilaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Upazila', 'District',
        function($scope, $stateParams, $uibModalInstance, entity, Upazila, District) {

        $scope.upazila = entity;
        $scope.districts = District.query();
        $scope.load = function(id) {
            Upazila.get({id : id}, function(result) {
                $scope.upazila = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:upazilaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.upazila.id != null) {
                Upazila.update($scope.upazila, onSaveSuccess, onSaveError);
            } else {
                Upazila.save($scope.upazila, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
