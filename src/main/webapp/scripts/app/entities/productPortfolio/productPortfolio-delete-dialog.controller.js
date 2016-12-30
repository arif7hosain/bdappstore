'use strict';

angular.module('appstoreApp')
	.controller('ProductPortfolioDeleteController', function($scope, $uibModalInstance, entity, ProductPortfolio) {

        $scope.productPortfolio = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProductPortfolio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
