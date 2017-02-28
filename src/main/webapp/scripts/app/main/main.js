'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: [],
                     pageTitle: 'Welcome to Leading Software Marketing Platform in Bangladesh !'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    }
                },
                resolve: {

                }
            })
            .state('home.result', {
                parent: 'site',
                url: '/search/{q}',
                data: {
                    authorities: [],
                     pageTitle: 'Welcome to Leading Software Marketing Platform in Bangladesh !'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/search-result.html',
                        controller: 'ResultController'
                    }
                },
               resolve: {
                       entity: ['$stateParams', 'Product', function($stateParams, Product) {
                           return Product.get({id : $stateParams.d});
                       }]
                   }
            });
    });
