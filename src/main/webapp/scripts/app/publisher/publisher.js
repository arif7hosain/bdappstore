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
                url: '/publisher/register/success/{email}',
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
                    pageTitle: 'Welcome to Company Dashboard !'
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
            });
    });
