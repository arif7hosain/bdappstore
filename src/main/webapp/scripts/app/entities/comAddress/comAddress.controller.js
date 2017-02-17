'use strict';

angular.module('appstoreApp')
    .controller('ComAddressController', function ($scope, $state, ComAddress, ComAddressSearch, ParseLinks) {

        $scope.comAddresss = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ComAddress.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.comAddresss = result;

            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ComAddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.comAddresss = result;
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
            $scope.comAddress = {
                addressType: null,
                officePhone: null,
                contactNumber: null,
                postalCode: null,
                house: null,
                RoadNo: null,
                city: null,
                CreatedDate: null,
                updatedDate: null,
                createBy: null,
                updatedBy: null,
                isActive: null,
                id: null
            };
        };
    });
