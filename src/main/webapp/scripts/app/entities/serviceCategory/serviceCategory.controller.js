'use strict';

angular.module('appstoreApp')
    .controller('ServiceCategoryController', function ($scope, $state, ServiceCategory, ServiceCategorySearch, ParseLinks) {

        $scope.serviceCategorys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ServiceCategory.query({page: $scope.page - 1, size: 3, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.serviceCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ServiceCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.serviceCategorys = result;
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
            $scope.serviceCategory = {
                serviceName: null,
                serviceDescription: null,
                shortDescription: null,
                CreatedDate: null,
                updatedDate: null,
                createBy: null,
                updatedBy: null,
                isActive: null,
                id: null
            };
        };
    });
