'use strict';

angular.module('appstoreApp')
    .controller('CompanyList', function ($scope, $state, DataUtils, ParseLinks,ActiveCompanies) {

        $scope.publishers=[];
        ActiveCompanies.query(function(d){
               $scope.publishers=d;
               console.log(d)
         });

    });
