'use strict';

angular.module('appstoreApp').controller('DistrictDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'District', 'Division',
        function($scope, $stateParams, $uibModalInstance, entity, District, Division) {

        $scope.district = entity;
        $scope.divisions = Division.query();
        $scope.load = function(id) {
            District.get({id : id}, function(result) {
                $scope.district = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:districtUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.district.id != null) {
                District.update($scope.district, onSaveSuccess, onSaveError);
            } else {
                District.save($scope.district, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
