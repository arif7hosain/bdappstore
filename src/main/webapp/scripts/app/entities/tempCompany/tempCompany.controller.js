'use strict';

angular.module('appstoreApp')
    .controller('TempCompanyController', function ($scope, $state, DataUtils, TempCompany, TempCompanySearch, ParseLinks,ApproveCompany) {

        $scope.tempCompanys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            TempCompany.query({page: $scope.page - 1, size: 500, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.tempCompanys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.approve=function(data){
            alert(data);
            ApproveCompany.query({id:data},function(results){
                console.log(results);
            });
        };



        $scope.search = function () {
            TempCompanySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.tempCompanys = result;
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
            $scope.tempCompany = {
                username: null,
                email: null,
                password: null,
                branchName: null,
                BranchType: null,
                companyName: null,
                shortName: null,
                companyInformation: null,
                businessDescription: null,
                facebook: null,
                googlePlus: null,
                youtube: null,
                linkedin: null,
                twitter: null,
                website: null,
                city: null,
                shortDescription: null,
                logo: null,
                logoContentType: null,
                companyType: null,
                addressType: null,
                officePhone: null,
                contactNumber: null,
                postalCode: null,
                house: null,
                RoadNo: null,
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
