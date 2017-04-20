'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('publisher', {
                parent: 'account',
                url: '/create-account',
                data: {
                    authorities: [],
                    pageTitle: 'Registration'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/create_publisher.html',
                           controller: 'CreatePublisher'
                       }
                   },
                    resolve: {
                    }
            })
            .state('publisher.success', {
                parent: 'account',
                url: '/publisher/register/success',
                data: {
                    authorities: [],
                    pageTitle: 'Registration Successful at Bangladeshi Largest App Store'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/success.html',
                           controller: 'PublisherSuccess'
                       }
                   },
                    resolve: {
                    }
            })
            .state('publisher.dashboard', {
                parent: 'account',
                url: '/company/dashboard',
                data: {
                    authorities: [],
                    pageTitle: 'Welcome to AppStore App Publisher !'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/dashboard.html',
                           controller: 'CompanyDashboard'
                       }
                   },
                    resolve: {
                    }
            })
            .state('publisher.profile', {
                parent: 'account',
                url: '/company/profile',
                data: {
                    authorities: [],
                    pageTitle: 'Welcome to Company Profile !'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/publisher-profile.html',
                           controller: 'PublisherProfileController'
                       }
                   },
                    resolve: {
                    }
            })
            .state('publisher.app-new', {
                parent: 'account',
                url: '/company/app-new',
                data: {
                    authorities: [],
                    pageTitle: 'Welcome to Company Profile !'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/product-dialog.html',
                           controller: 'AppNew'
                       }
                   },
                    resolve: {
                    }
            })
            .state('publisher.app-edit', {
                 parent: 'account',
                 url: '/company/app-edit/{id}',
                 data: {
                     authorities: ['ROLE_COMPANY'],
                     pageTitle: 'Welcome to app edit page !'
                 },
                 views: {
                     'content@': {
                         templateUrl: 'scripts/app/publisher/product-dialog.html',
                         controller: 'AppNew'
                     }
                 },
                 resolve: {
                     entity: ['$stateParams', 'Product', function($stateParams, Product) {
                         return Product.get({id : $stateParams.id});
                     }]
                 }
             });
    });
