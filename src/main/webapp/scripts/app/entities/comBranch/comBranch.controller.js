'use strict';

angular.module('appstoreApp')
    .controller('ComBranchController', function ($scope, $state, ComBranch, ComBranchSearch, ParseLinks) {

        $scope.comBranchs = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ComBranch.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.comBranchs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ComBranchSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.comBranchs = result;
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
            $scope.comBranch = {
                branchName: null,
                branchDescription: null,
                branchType: null,
                facebook: null,
                googlePlus: null,
                youtube: null,
                linkedin: null,
                twitter: null,
                website: null,
                city: null,
                createdDate: null,
                updatedDate: null,
                createBy: null,
                updatedBy: null,
                isActive: null,
                id: null
            };
        };
    });
