'use strict';
angular.module('appstoreApp')
.controller('CompanyDashboard',['$scope', '$stateParams', 'DataUtils','GetAppsByCompany','Product','ProductPortfolio',
function($scope, $stateParams, DataUtils,GetAppsByCompany,Product,ProductPortfolio){

    $scope.apps=[];
    $scope.photos=[];
    $scope.photo={};
    $scope.app_view=function(id){
        Product.get({id: id}, function(result) {
            $scope.product = result;
        });
    };
    $scope.app_img=function(id){
        Product.get({id: id}, function(result) {
            $scope.product = result;
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


$scope.photo.image=null;
$scope.photo.imageContentType=null;
$scope.photo.product=$scope.product;

//    image====================================
        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;
        $scope.setImage = function ($file, productPortfolio) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        productPortfolio.image = base64Data;
                        productPortfolio.imageContentType = $file.type;
                    });
                };
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('appstoreApp:productPortfolioUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };


            $scope.add_all_image=function(){
            $scope.photo.product=$scope.product;
                $scope.isSaving = true;
                if ($scope.photo.id != null) {
                    ProductPortfolio.update($scope.photo, onSaveSuccess, onSaveError);
                } else {
                    ProductPortfolio.save($scope.photo, onSaveSuccess, onSaveError);
                }
            };


}]);
