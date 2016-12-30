'use strict';

angular.module('appstoreApp')
    .controller('ProductPortfolioController', function ($scope, $state, DataUtils, ProductPortfolio, ProductPortfolioSearch, ParseLinks) {

        $scope.productPortfolios = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ProductPortfolio.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.productPortfolios = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProductPortfolioSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.productPortfolios = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.productPortfolio = {
                image: null,
                imageContentType: null,
                CreatedDate: null,
                updatedDate: null,
                createBy: null,
                updatedBy: null,
                isActive: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
