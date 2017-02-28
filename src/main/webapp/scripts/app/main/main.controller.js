'use strict';

angular.module('appstoreApp')
    .controller('MainController', function ($scope,$state,Principal) {
     $scope.getProducts=function(data){
          $state.go('home.result({q:data})', null, {reload: true});
         alert(data);
     };
    });
