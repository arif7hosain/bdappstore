'use strict';
angular.module('appstoreApp')
.controller('CompanyDashboard',['$scope', '$stateParams', 'DataUtils','GetAppsByCompany',
function($scope, $stateParams, DataUtils,GetAppsByCompany){

    $scope.apps=[];
    GetAppsByCompany.query(function(apps){ $scope.apps=apps;});

}]);
