'use strict';

angular.module('appstoreApp')
    .controller('SoftwareCategoryController', function ($scope, $state, SoftwareCategory, SoftwareCategorySearch, ParseLinks) {

        $scope.softwareCategorys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SoftwareCategory.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.softwareCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SoftwareCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.softwareCategorys = result;
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
            $scope.softwareCategory = {
                categoryName: null,
                description: null,
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
