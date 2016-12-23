'use strict';

angular.module('appstoreApp')
    .controller('CompanyInformationController', function ($scope, $state, DataUtils, CompanyInformation, CompanyInformationSearch, ParseLinks) {

        $scope.companyInformations = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            CompanyInformation.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.companyInformations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CompanyInformationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.companyInformations = result;
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
            $scope.companyInformation = {
                companyName: null,
                shortName: null,
                companyInformation: null,
                businessDescription: null,
                shortDescription: null,
                logo: null,
                logoContentType: null,
                website: null,
                companyType: null,
                CreatedDate: null,
                updatedDate: null,
                createBy: null,
                updatedBy: null,
                activeStatus: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
