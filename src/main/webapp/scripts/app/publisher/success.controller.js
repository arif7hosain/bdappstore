'use strict';
angular.module('appstoreApp').controller('PublisherSuccess',
['$scope','$state', '$stateParams', 'DataUtils',
function($scope, $state, $stateParams, DataUtils){

alert('mail: '+$stateParams.email);


}]);
