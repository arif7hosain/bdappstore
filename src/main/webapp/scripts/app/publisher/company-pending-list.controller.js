'use strict';

angular.module('appstoreApp')
    .controller('PendingPublisherController', function ($scope, $state, DataUtils, ParseLinks,GetPendingCompany,ApproveCompany) {

        $scope.publishers = [];
        GetPendingCompany.query(function(data){
            $scope.publishers=data;
            console.log(data)
        });

        $scope.approve=function(id){
            ApproveCompany.query({id:id},function(results){
                console.log(results);
            });
        };
    });
