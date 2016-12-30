'use strict';

angular.module('appstoreApp')
    .controller('ProductPortfolioDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, ProductPortfolio, Product) {
        $scope.productPortfolio = entity;
        $scope.load = function (id) {
            ProductPortfolio.get({id: id}, function(result) {
                $scope.productPortfolio = result;
            });
        };
        var unsubscribe = $rootScope.$on('appstoreApp:productPortfolioUpdate', function(event, result) {
            $scope.productPortfolio = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
