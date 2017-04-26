'use strict';
angular.module('appstoreApp')
.controller('CompanyDashboard',['$scope', '$stateParams', 'DataUtils','GetAppsByCompany','Product',
function($scope, $stateParams, DataUtils,GetAppsByCompany,Product){

    $scope.apps=[];
    $scope.app_view=function(id){
        Product.get({id: id}, function(result) {
            $scope.product = result;
            console.log('--')
            console.log(result);
        });
    };

    GetAppsByCompany.query(function(apps){ $scope.apps=apps;
    console.log('-->>')
    console.log(apps)
     $scope.allApp=[];
     $scope.a_app={};
        for(var i=0; i<apps.length; i++){
            $scope.a_app.label=apps[i].productTitle;
            $scope.a_app.y=apps[i].view;
            $scope.allApp.push($scope.a_app);
            $scope.a_app={};
        }
        	var chart = new CanvasJS.Chart("chartContainer", {
        		title:{
        			text: "Apps Viewed by Customers"
        		},
        		data: [
        		{
        			// Change type to "doughnut", "line", "splineArea", etc.
        			type: "column",
        			dataPoints: $scope.allApp
        		}
        		],
        		exportEnabled: true
        	});
        	chart.render();
    });
}]);
